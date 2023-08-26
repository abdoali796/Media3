package com.abdoali.mymidia3.ui.online

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abdoali.datasourece.QuranItem
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.mymidia3.ui.online.ListMp

@Composable
fun ListX(
    quran: List<QuranItem> ,
    uiEvent: (UIEvent) -> Unit ,
    modifier: Modifier = Modifier
){
    ListMp(
        quran ,
        uiEvent , modifier
    )
}