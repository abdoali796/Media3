package com.abdoali.datasourece.helper

import com.abdoali.datasourece.QuranItem

fun List<QuranItem>.isLocal()  =this.filter { it.isLocal }


