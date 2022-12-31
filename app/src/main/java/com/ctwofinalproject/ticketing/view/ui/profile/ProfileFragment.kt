package com.ctwofinalproject.ticketing.view.ui.profile

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.data.UserUpdate
import com.ctwofinalproject.ticketing.databinding.FragmentProfileBinding
import com.ctwofinalproject.ticketing.helper.AuthenticationError
import com.ctwofinalproject.ticketing.util.ShowSnack
import com.ctwofinalproject.ticketing.util.TokenNav
import com.ctwofinalproject.ticketing.viewmodel.HomeViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProfileViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*
import java.nio.file.Files.createFile
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding?                         = null
    private val binding get()                                              = _binding!!
    private val viewModelProto                                             : ProtoViewModel by viewModels()
    private val homeViewModel                                              : HomeViewModel by viewModels()
    private val profileViewModel                                           : ProfileViewModel by viewModels()
    private lateinit var builder                                           : AlertDialog.Builder
    lateinit var sharedPref                                                : SharedPreferences
    lateinit var editPref                                                  : SharedPreferences.Editor
    lateinit var sharedPrefBiometric                                       : SharedPreferences
    lateinit var editPrefBiometric                                         : SharedPreferences.Editor
    private var token                                                      = ""
    private lateinit var image                                             : MultipartBody.Part
    private var isLogin                                                    = false
    private lateinit var biometricPrompt                                   : BiometricPrompt
    private lateinit var biometricManager                                  : BiometricManager

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            val contentResolver = requireActivity().contentResolver
            val type = contentResolver.getType(result!!)

            val tempFile = File.createTempFile("image", ".jpg",null)
            val inputStream = contentResolver.openInputStream(result!!)
            tempFile.outputStream().use {
                inputStream?.copyTo(it)
            }
            val reqBody : RequestBody = tempFile.asRequestBody(type!!.toMediaType())
            image = MultipartBody.Part.createFormData("image", tempFile.name, reqBody)
        }
    private val REQUEST_CODE_PERMISSION = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        builder                                                     = AlertDialog.Builder(requireActivity())
        sharedPref                                                  = requireContext().getSharedPreferences("sharedairport", Context.MODE_PRIVATE)
        editPref                                                    = sharedPref.edit()
        sharedPrefBiometric                                         = requireContext().getSharedPreferences("sharedBiometric", Context.MODE_PRIVATE)
        editPrefBiometric                                           = sharedPrefBiometric.edit()
        initListener()
        setImageSlider()
        setBottomNav()
        setProfile("")
        setupBiometricAuthentication()
        checkBiometricFeatureState()

        viewModelProto.dataUser.observe(viewLifecycleOwner){
            if(it.isLogin){
                isLogin = true
                token = it.token
                profileViewModel.whoami("bearer "+token)
            } else {
                isLogin = false
                binding.tvNameFragmentProfile.text = "Guest"
                binding.btnLogoutFprofile.text = "Login"
                Log.d(TAG, "onViewCreated: need to be logged in")
            }
        }

        profileViewModel.liveDataResponseWhoami.observe(viewLifecycleOwner){
            Log.d(TAG, "onViewCreated: ${it}")
            if(it != null){
                setProfile(it.currentUser!!.pictures.toString())
                binding.tvNameFragmentProfile.setText(it.currentUser!!.firstname.toString()+" "+it.currentUser!!.lastname.toString())
            } else {
                
            }
        }
        
        profileViewModel.liveDataResponsePostFile.observe(viewLifecycleOwner){
            Log.d(TAG, "onViewCreated: ${it}")
            if(it != null){
                if(it.code!!.equals(200)){
                    ShowSnack.show(binding.root,"Success Upload Profile")
                    setProfile(it.data!!)
                    profileViewModel.updateUser("bearer "+token, UserUpdate(null,null,null,null,null,null,
                        null,it.data.toString(),null,null,null))
                    profileViewModel.liveDataResponsePostFile.value = null
                }
            }
        }

    }

    private fun setupBiometricAuthentication() {
        biometricManager = BiometricManager.from(requireContext())
        val executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this, executor, biometricCallback)
    }

    private fun checkBiometricFeatureState() {
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> Log.d(
                TAG,
                "checkBiometricFeatureState: Sorry. It seems your device has no biometric hardware"
            )
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
            Log.d(
                TAG,
                "Biometric features are currently unavailable."
            )
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
            Log.d(
                TAG,
                "You have not registered any biometric credentials"
            )
            BiometricManager.BIOMETRIC_SUCCESS -> {}
        }
    }

    private fun buildBiometricPrompt(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("Verify your identity")
            .setDescription("Confirm your identity so we can verify it's you")
            .setNegativeButtonText("Cancel")
            .setConfirmationRequired(false) //Allows user to authenticate without performing an action, such as pressing a button, after their biometric credential is accepted.
            .build()
    }

    private fun isBiometricFeatureAvailable(): Boolean {
        return biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS
    }

    private val biometricCallback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_detailProfileFragment)
            Log.d(TAG, "onAuthenticationSucceeded: authentication success")
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)

            if (errorCode != AuthenticationError.AUTHENTICATION_DIALOG_DISMISSED.errorCode && errorCode != AuthenticationError.CANCELLED.errorCode) {
                Log.d(TAG, "onAuthenticationSucceeded: authentication failed ${errString.toString()}")
            }
        }
    }


    private fun initListener() {
        binding?.run {
            btnLogoutFprofile.setOnClickListener {
                if(isLogin){
                    builder.setTitle("Logout")
                        .setMessage("Are you sure want to logout ?")
                        .setCancelable(true)
                        .setPositiveButton("Yes",DialogInterface.OnClickListener { dialogInterface, i ->
                            clearAllLocal()
                            dialogInterface.dismiss()
                            ShowSnack.show(binding.root,"Logout Successful")
                            profileViewModel.logout("bearer "+token)
                            var bund = Bundle()
                            bund.putString("fromWhere", "profile")
                            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_loginFragment,bund)
                        })
                        .setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->
                            dialogInterface.dismiss()
                        })
                        .show()
                } else {
                    var bund = Bundle()
                    bund.putString("fromWhere", "profile")
                    Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_loginFragment,bund)
                }
            }

            btnLanguageFprofile.setOnClickListener {
                val languageItems = arrayOf("Bahasa", "English")
                var checkedItem = 1
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Choose Language")
                    .setSingleChoiceItems(languageItems, checkedItem) { dialog_, which ->
                        checkedItem = which
                        Log.d(TAG, "initListener: ${languageItems}")
                    }
                    .setPositiveButton("Ok") { dialog, which ->
                        when(checkedItem){
                            0 -> setLanguage("id")
                            1 -> setLanguage("en")
                        }
                        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_splashFragment)
                        ShowSnack.show(binding.root,"Language Changed")
                    }
                    .setNegativeButton("Cancel") { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
            }

            tvOpenMyProfileFMyProfile.setOnClickListener {
                if(isLogin){
                    if(sharedPrefBiometric.getBoolean("biometricSettings",false)){
                        if (isBiometricFeatureAvailable()) {
                            biometricPrompt.authenticate(buildBiometricPrompt())
                        }
                    } else {
                        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_detailProfileFragment)
                    }
                } else {
                    builder.setTitle("Notification")
                        .setMessage("To access these feature , you need to be loggedin first")
                        .setCancelable(true)
                        .setPositiveButton("Goto Login",DialogInterface.OnClickListener { dialogInterface, i ->
                            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_loginFragment)
                            dialogInterface.dismiss()
                        })
                        .setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->
                            dialogInterface.dismiss()
                        })
                        .show()
                }
            }

            btnBiometricFprofile.setOnClickListener {
                builder.setTitle("Biometric Authentication")
                    .setMessage("Biometric Authentication Settings")
                    .setCancelable(true)
                    .setPositiveButton("Enabled",DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.dismiss()
                        editPrefBiometric.putBoolean("biometricSettings",true)
                        editPrefBiometric.apply()
                        ShowSnack.show(binding.root,"Biometic Authentication Enabled")
                    })
                    .setNegativeButton("Disabled",DialogInterface.OnClickListener { dialogInterface, i ->
                        editPrefBiometric.putBoolean("biometricSettings",false)
                        editPrefBiometric.apply()
                        dialogInterface.dismiss()
                    })
                    .show()
            }

            ivProfileFragmentProfile.setOnClickListener {
                openFile()
            }
        }
    }

    private fun openFile() {
        requireActivity().intent.type = "image/*"
        openFile.launch("image/*")
    }

    private val openFile = registerForActivityResult(ActivityResultContracts.GetContent()){
        if(it != null){
           val contentResolver = activity?.contentResolver
            val type = contentResolver?.getType(it)

            val tempFile = File.createTempFile("image",".jpg",null)
            val inputStream = contentResolver?.openInputStream(it)
            tempFile.outputStream().use {
                inputStream?.copyTo(it)
            }
            val imageBody : RequestBody = tempFile.asRequestBody(type!!.toMediaType())
            var imageReq : MultipartBody.Part = MultipartBody.Part.createFormData("file",tempFile.name,imageBody)
            profileViewModel.postFile(imageReq)
            //ShowSnack.show(binding.root,"Upload Profile Success")
        }
    }

    private fun setImageSlider(){
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://firebasestorage.googleapis.com/v0/b/mynotes-f6709.appspot.com/o/images%2FUpgrade%20ke%20premium.png?alt=media&token=0c04e7cd-a4e8-4ce8-9281-04a9762d1133","Premium card"))

        binding.imageSliderProfile.setImageList(imageList, ScaleTypes.FIT)
    }


    private fun setProfile(urlImage : String){
        Glide.with(this)
            .load(urlImage)
            .error(R.drawable.img_guest)
            .circleCrop()
            .into(binding.ivProfileFragmentProfile)
    }
    private fun clearAllLocal(){
        viewModelProto.clearData()
        viewModelProto.clearDataBooking()
        homeViewModel.deleteAllRecentSearch()
        editPref.clear().commit()
    }

    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility = View.VISIBLE
    }

    private fun setLanguage(lang: String?) {
        val myLocale = Locale(lang)
        val res = resources
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, res.displayMetrics)
    }
}