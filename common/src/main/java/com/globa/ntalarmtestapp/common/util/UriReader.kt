package com.globa.ntalarmtestapp.common.util

import android.content.Context
import android.net.Uri
import java.io.FileInputStream

fun readUri(context: Context, uri: Uri): ByteArray? {
    val pfd = context.contentResolver.openFileDescriptor(uri, "r") ?: return null
    val data = ByteArray(pfd.statSize.toInt())
    if (data.isEmpty()) return null
    val fd = pfd.fileDescriptor
    val fileStream = FileInputStream(fd)
    fileStream.read(data)
    pfd.close()
    return data
}