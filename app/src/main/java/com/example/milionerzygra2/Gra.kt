package com.example.milionerzygra2

import com.example.milionerzygra2.models.EtapyKwotyEnum
import java.lang.Exception

public class Gra
{
    companion object {
        private var _etapGryKwota = EtapyKwotyEnum._0;
        private var _nazwaUzytkownika = "";

        public fun KoniecGryDajWygranaIZerujGre() : Int
        {
            var kwotaWygrana = 0;
            if (_etapGryKwota == EtapyKwotyEnum._1000000){
                kwotaWygrana = EtapyKwotyEnum._1000000.kwota;
            }
            if (_etapGryKwota.kwota >= EtapyKwotyEnum._40000.kwota){
                return EtapyKwotyEnum._40000.kwota;
            }
            if (_etapGryKwota.kwota >= EtapyKwotyEnum._1000.kwota){
                return EtapyKwotyEnum._1000.kwota;
            }

            zerujGreZostawUzytkownika()

            return kwotaWygrana;
        }

        public fun PokazAktualnaKwoteEtapu() : Int
        {
            return _etapGryKwota.kwota
        }

        /**
         * @return Jesli nie ma nic dalej, czyli aktualnie milion, to null
         */
        public fun PokazKwoteNastepnegoPytania() : Int?
        {
            val values = EtapyKwotyEnum.values()
            val index = values.indexOf(_etapGryKwota)

            try{
                return values[index + 1].kwota;
            }
            catch (e: Exception){
                return null;
            }
        }
        public fun ZacznijGre(nazwaUzyt: String)
        {
            _nazwaUzytkownika = nazwaUzyt;
            zerujGreZostawUzytkownika()
        }

        private fun zerujGreZostawUzytkownika()
        {
            _etapGryKwota = EtapyKwotyEnum._0;
        }

        public fun PrzejdzDoNastepnegoEtapu() {
            val values = EtapyKwotyEnum.values()
            val index = values.indexOf(_etapGryKwota)
            _etapGryKwota = values[index + 1];
        }
    }
}