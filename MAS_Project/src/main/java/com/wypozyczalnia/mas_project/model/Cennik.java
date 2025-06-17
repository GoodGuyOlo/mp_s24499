package com.wypozyczalnia.mas_project.model;

import java.util.EnumMap;

    /* todo
        - updateStawkaPodstawowa()
        - updateStawkaWeekendowa()
        - uporządkować metody i zmienne
     */

public abstract class Cennik {
    private static final EnumMap<KategoriaSamochodu, Double> stawkiPodstawowe = new EnumMap<>(KategoriaSamochodu.class);
    private static final EnumMap<KategoriaSamochodu, Double> stawkiWeekendowe = new EnumMap<>(KategoriaSamochodu.class);

    static {
        // Stawki podstawowe (pn-czw)
        stawkiPodstawowe.put(KategoriaSamochodu.SPORTOWE, 500.0);
        stawkiPodstawowe.put(KategoriaSamochodu.SUV, 300.0);
        stawkiPodstawowe.put(KategoriaSamochodu.LUKSUSOWE, 700.0);
        stawkiPodstawowe.put(KategoriaSamochodu.MIEJSKIE, 150.0);

        // Stawki weekendowe (pt-nd)
        stawkiWeekendowe.put(KategoriaSamochodu.SPORTOWE, 650.0);
        stawkiWeekendowe.put(KategoriaSamochodu.SUV, 400.0);
        stawkiWeekendowe.put(KategoriaSamochodu.LUKSUSOWE, 850.0);
        stawkiWeekendowe.put(KategoriaSamochodu.MIEJSKIE, 200.0);
    }

    public static double getStawkaPodstawowa(KategoriaSamochodu kategoriaSamochodu) {
        return stawkiPodstawowe.get(kategoriaSamochodu);
    }

    public static double getStawkaWeekendowa(KategoriaSamochodu kategoriaSamochodu) {
        return stawkiWeekendowe.get(kategoriaSamochodu);
    }

}