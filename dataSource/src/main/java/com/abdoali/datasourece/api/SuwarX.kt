package com.abdoali.datasourece.api

import android.util.Log
import java.util.Locale


fun surahString(int: Int): String {


    return surah()[int]
}

object SurahSort {

    val sura = listOf(
        "المدثر" ,


        "القيامة" ,


        "الإنسان" ,


        "المرسلات" ,


        "النبأ" ,


        "النازعات" ,


        "عبس" ,


        "التكوير" ,


        "الإنفطار" ,


        "المطففين" ,


        "الإنشقاق" ,


        "البروج" ,


        "الطارق" ,


        "الأعلى" ,


        "الغاشية" ,


        "الفجر" ,


        "البلد" ,


        "الشمس" ,


        "الليل" ,


        "الضحى" ,


        "الشرح" ,


        "التين" ,


        "العلق" ,


        "القدر" ,


        "البينة" ,


        "الزلزلة" ,


        "العاديات" ,


        "القارعة" ,


        "التكاثر" ,


        "العصر" ,


        "الهمزة" ,


        "الفيل" ,


        "قريش" ,


        "الماعون" ,


        "الكوثر" ,


        "الكافرون" ,


        "النصر" ,


        "المسد" ,


        "الإخلاص" ,


        "الفلق" ,


        "الناس" ,
    )
}

fun surah():List<String> {

     val suraAr = listOf(
        "" ,
        "الفاتحة" ,


        "البقرة" ,


        "آل عمران" ,


        "النساء" ,


        "المائدة" ,


        "الأنعام" ,


        "الأعراف" ,


        "الأنفال" ,


        "التوبة" ,


        "يونس" ,


        "هود" ,


        "يوسف" ,


        "الرعد" ,


        "إبراهيم" ,


        "الحجر" ,


        "النحل" ,


        "الإسراء" ,


        "الكهف" ,


        "مريم" ,


        "طه" ,


        "الأنبياء" ,


        "الحج" ,


        "المؤمنون" ,


        "النور" ,


        "الفرقان" ,


        "الشعراء" ,


        "النمل" ,


        "القصص" ,


        "العنكبوت" ,


        "الروم" ,


        "لقمان" ,


        "السجدة" ,


        "الأحزاب" ,


        "سبأ" ,


        "فاطر" ,


        "يس" ,


        "الصافات" ,


        "ص" ,


        "الزمر" ,


        "غافر" ,


        "فصلت" ,


        "الشورى" ,


        "الزخرف" ,


        "الدّخان" ,


        "الجاثية" ,


        "الأحقاف" ,


        "محمد" ,


        "الفتح" ,


        "الحجرات" ,


        "ق" ,


        "الذاريات" ,


        "الطور" ,


        "النجم" ,


        "القمر" ,


        "الرحمن" ,


        "الواقعة" ,


        "الحديد" ,


        "المجادلة" ,


        "الحشر" ,


        "الممتحنة" ,


        "الصف" ,


        "الجمعة" ,


        "المنافقون" ,


        "التغابن" ,


        "الطلاق" ,


        "التحريم" ,


        "الملك" ,


        "القلم" ,


        "الحاقة" ,


        "المعارج" ,


        "نوح" ,


        "الجن" ,


        "المزمل" ,


        "المدثر" ,


        "القيامة" ,


        "الإنسان" ,


        "المرسلات" ,


        "النبأ" ,


        "النازعات" ,


        "عبس" ,


        "التكوير" ,


        "الإنفطار" ,


        "المطففين" ,


        "الإنشقاق" ,


        "البروج" ,


        "الطارق" ,


        "الأعلى" ,


        "الغاشية" ,


        "الفجر" ,


        "البلد" ,


        "الشمس" ,


        "الليل" ,


        "الضحى" ,


        "الشرح" ,


        "التين" ,


        "العلق" ,


        "القدر" ,


        "البينة" ,


        "الزلزلة" ,


        "العاديات" ,


        "القارعة" ,


        "التكاثر" ,


        "العصر" ,


        "الهمزة" ,


        "الفيل" ,


        "قريش" ,


        "الماعون" ,


        "الكوثر" ,


        "الكافرون" ,


        "النصر" ,


        "المسد" ,


        "الإخلاص" ,


        "الفلق" ,


        "الناس" ,


        )
     val suraEng = listOf<String>(
        "" ,


        "Al-Fatihah " ,


        "Al-Baqarah " ,


        "Al-Imran " ,


        "An-Nisa' " ,


        "Al-Ma'idah " ,


        "Al-An'am " ,


        "Al-A'raf " ,


        "Al-Anfal " ,


        "At-Taubah " ,


        "Yunus " ,


        "Hood " ,


        "Yusuf " ,


        "Ar-Ra'd " ,


        "Ibrahim " ,


        "Al-Hijr " ,


        "An-Nahl " ,


        "Al-Isra " ,


        "Al-Kahf " ,


        "Maryam " ,


        "Ta­Ha " ,


        "Al-Anbiya' " ,


        "Al-Hajj " ,


        "Al-Mu'minun " ,


        "An-Nur " ,


        "Al-Furqan " ,


        "Ash-Shu'ara' " ,


        "An-Naml " ,


        "Al-Qasas " ,


        "Al-'Ankabut " ,


        "Ar­Room" ,


        "Luqman " ,


        "As­Sajdah " ,


        "Al­Ahzab " ,


        "Saba' " ,


        "Fatir " ,


        "Ya­Sin " ,


        "As-Saffat " ,


        "Sad " ,


        "Az-Zumar " ,


        "Ghafir " ,


        "Fussilat " ,


        "Ash-Shura " ,


        "Az-Zukhruf " ,


        "Ad-Dukhan " ,


        "Al-Jathiya " ,


        "Al-Ahqaf " ,


        "Muhammad " ,


        "Al-Fath " ,


        "Al-Hujurat " ,


        "Qaf " ,


        "Az-Zariyat " ,


        "At-Tur " ,


        "An-Najm " ,


        "Al-Qamar " ,


        "Ar-Rahman " ,


        "Al-Waqi'ah " ,


        "Al-Hadid " ,


        "Al-Mujadilah " ,


        "Al-Hashr " ,


        "Al-Mumtahinah " ,


        "As-Saff " ,


        "Al-Jumu'ah " ,


        "Al-Munafiqun " ,


        "At-Taghabun " ,


        "At-Talaq " ,


        "At-Tahrim " ,


        "Al-Mulk " ,


        "Al-Qalam " ,


        "Al-Haqqah " ,


        "Al-Ma'arij " ,


        "Nooh " ,


        "Al-Jinn " ,


        "Al-Muzzammil " ,


        "Al-Muddaththir " ,


        "Al-Qiyamah " ,


        "Al-Insan " ,


        "Al-Mursalat " ,


        "An-Naba' " ,


        "An-Nazi'at " ,


        "Abasa" ,


        "At-Takwir " ,


        "Al-Infitar " ,


        "Al-Mutaffifin " ,


        "Al-Inshiqaq " ,


        "Al-Buruj " ,


        "At-Tariq " ,


        "Al-A'la " ,


        "Al-Ghashiyah " ,


        "Al-Fajr " ,


        "Al-Balad " ,


        "Ash-Shams " ,


        "Al-Lail " ,


        "Ad-Duha " ,


        "Ash-Sharh " ,


        "At-Tin " ,


        "Al-'Alaq " ,


        "Al-Qadr " ,


        "Al-Baiyinah " ,


        "Az-Zalzalah " ,


        "Al-'Adiyat " ,


        "Al-Qari'ah " ,


        "At-Takathur " ,


        "Al-'Asr " ,


        "Al-Humazah " ,


        "Al-Fil " ,


        "Quraish " ,


        "Al-Ma'un " ,


        "Al-Kauthar " ,


        "Al-Kafirun " ,


        "An-Nasr " ,


        "Al-Masad " ,


        "Al-Ikhlas " ,


        "Al-Falaq " ,


        "An-Nas" ,


        )
     val localeLanguage= Locale.getDefault().isO3Language
    return if (localeLanguage == "ara") suraAr else suraEng
}