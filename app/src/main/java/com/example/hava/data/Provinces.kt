package com.example.hava.data

import java.text.Collator
import java.util.Locale

data class Province(
    val name: String,
    val latitude: Double,
    val longitude: Double
)

/** 81 ilin şehir merkezi koordinatları. İl listesi değişmediği için API yerine burada tutuluyor. */
object Provinces {

    val all: List<Province> by lazy {
        val collator = Collator.getInstance(Locale.forLanguageTag("tr-TR"))
        raw.sortedWith { a, b -> collator.compare(a.name, b.name) }
    }

    private val raw = listOf(
        Province("Adana", 37.0000, 35.3213),
        Province("Adıyaman", 37.7648, 38.2786),
        Province("Afyonkarahisar", 38.7507, 30.5567),
        Province("Ağrı", 39.7191, 43.0503),
        Province("Aksaray", 38.3687, 34.0370),
        Province("Amasya", 40.6499, 35.8353),
        Province("Ankara", 39.9334, 32.8597),
        Province("Antalya", 36.8969, 30.7133),
        Province("Ardahan", 41.1105, 42.7022),
        Province("Artvin", 41.1828, 41.8183),
        Province("Aydın", 37.8560, 27.8416),
        Province("Balıkesir", 39.6484, 27.8826),
        Province("Bartın", 41.6344, 32.3375),
        Province("Batman", 37.8812, 41.1351),
        Province("Bayburt", 40.2552, 40.2249),
        Province("Bilecik", 40.1451, 29.9792),
        Province("Bingöl", 38.8854, 40.4980),
        Province("Bitlis", 38.4006, 42.1095),
        Province("Bolu", 40.7350, 31.6061),
        Province("Burdur", 37.7203, 30.2908),
        Province("Bursa", 40.1826, 29.0665),
        Province("Çanakkale", 40.1553, 26.4142),
        Province("Çankırı", 40.6013, 33.6134),
        Province("Çorum", 40.5506, 34.9556),
        Province("Denizli", 37.7765, 29.0864),
        Province("Diyarbakır", 37.9144, 40.2306),
        Province("Düzce", 40.8438, 31.1565),
        Province("Edirne", 41.6818, 26.5623),
        Province("Elazığ", 38.6810, 39.2264),
        Province("Erzincan", 39.7500, 39.5000),
        Province("Erzurum", 39.9043, 41.2679),
        Province("Eskişehir", 39.7767, 30.5206),
        Province("Gaziantep", 37.0662, 37.3833),
        Province("Giresun", 40.9128, 38.3895),
        Province("Gümüşhane", 40.4602, 39.4814),
        Province("Hakkâri", 37.5744, 43.7408),
        Province("Hatay", 36.4018, 36.3498),
        Province("Iğdır", 39.9237, 44.0450),
        Province("Isparta", 37.7648, 30.5566),
        Province("İstanbul", 41.0082, 28.9784),
        Province("İzmir", 38.4237, 27.1428),
        Province("Kahramanmaraş", 37.5858, 36.9371),
        Province("Karabük", 41.2061, 32.6204),
        Province("Karaman", 37.1759, 33.2287),
        Province("Kars", 40.6013, 43.0975),
        Province("Kastamonu", 41.3887, 33.7827),
        Province("Kayseri", 38.7312, 35.4787),
        Province("Kırıkkale", 39.8468, 33.5153),
        Province("Kırklareli", 41.7333, 27.2167),
        Province("Kırşehir", 39.1425, 34.1709),
        Province("Kilis", 36.7184, 37.1212),
        Province("Kocaeli", 40.8533, 29.8815),
        Province("Konya", 37.8746, 32.4932),
        Province("Kütahya", 39.4167, 29.9833),
        Province("Malatya", 38.3552, 38.3095),
        Province("Manisa", 38.6191, 27.4289),
        Province("Mardin", 37.3212, 40.7245),
        Province("Mersin", 36.8000, 34.6333),
        Province("Muğla", 37.2153, 28.3636),
        Province("Muş", 38.9462, 41.7539),
        Province("Nevşehir", 38.6939, 34.6857),
        Province("Niğde", 37.9667, 34.6833),
        Province("Ordu", 40.9839, 37.8764),
        Province("Osmaniye", 37.0742, 36.2478),
        Province("Rize", 41.0201, 40.5234),
        Province("Sakarya", 40.7569, 30.3783),
        Province("Samsun", 41.2867, 36.3300),
        Province("Siirt", 37.9333, 41.9500),
        Province("Sinop", 42.0231, 35.1531),
        Province("Sivas", 39.7477, 37.0179),
        Province("Şanlıurfa", 37.1591, 38.7969),
        Province("Şırnak", 37.5164, 42.4611),
        Province("Tekirdağ", 40.9833, 27.5167),
        Province("Tokat", 40.3167, 36.5500),
        Province("Trabzon", 41.0015, 39.7178),
        Province("Tunceli", 39.1079, 39.5401),
        Province("Uşak", 38.6823, 29.4082),
        Province("Van", 38.4891, 43.4089),
        Province("Yalova", 40.6500, 29.2667),
        Province("Yozgat", 39.8181, 34.8147),
        Province("Zonguldak", 41.4564, 31.7987)
    )
}
