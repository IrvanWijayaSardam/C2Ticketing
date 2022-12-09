package com.ctwofinalproject.ticketing.proto

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.ctwofinalproject.ticketing.BookingDataProto
import java.io.InputStream
import java.io.OutputStream

object BookingDataPreferencesSerializer : Serializer<BookingDataProto> {
    override val defaultValue: BookingDataProto
        get() = BookingDataProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): BookingDataProto {
        try{
            return BookingDataProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException){
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: BookingDataProto, output: OutputStream) = t.writeTo(output)
}