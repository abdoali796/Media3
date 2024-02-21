package com.abdoali.mymidia3.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.abdoali.mymidia3.R

// Set of Material typography styles to start with
val hafs= FontFamily( Font(com.abdoali.playservice.R.font.hafs_smart_08))
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = hafs ,
        fontWeight = FontWeight.Normal ,
        fontSize = 16.sp ,
        lineHeight = 24.sp ,
        letterSpacing = 0.5.sp
    )
    ,
    titleLarge = TextStyle(
        fontFamily = hafs,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = hafs,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),


)