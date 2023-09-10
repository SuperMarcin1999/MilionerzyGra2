package com.example.milionerzygra2.controllers

import android.content.Context
import com.example.milionerzygra2.MyDatabase
import com.example.milionerzygra2.entities.PoziomTrudnosciEntity
import com.example.milionerzygra2.entities.PytanieEntity
import com.example.milionerzygra2.models.PoprawnaOdpEnum
import com.example.milionerzygra2.models.PoziomTrudnosciEnum
import com.example.milionerzygra2.models.PytanieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class PytanieController {

    private lateinit var _dostepnePytaniaLatwe : MutableList<PytanieModel>;
    private lateinit var _dostepnePytaniaSrednie : MutableList<PytanieModel>;
    private lateinit var _dostepnePytaniaTrudne : MutableList<PytanieModel>;


    constructor(applicationContext: Context){
        _dostepnePytaniaLatwe = mutableListOf()
        _dostepnePytaniaSrednie = mutableListOf()
        _dostepnePytaniaTrudne = mutableListOf()

        runBlocking {
            launch(Dispatchers.IO) {
                val instance = MyDatabase.getDatabase(applicationContext);
                val pytaniaEntities = instance.pytanieDao().getAllPytania();
                val poziomyEntities = instance.poziomTrudnosciDao().getAllPoziomyTrunosci();

                for (pytanieEnt in pytaniaEntities){
                    var model = mapPytanieEntityToModel(pytanieEnt, poziomyEntities);
                    if(model.poziom == PoziomTrudnosciEnum.LATWE){
                        _dostepnePytaniaLatwe.add(model);
                    }
                    if(model.poziom == PoziomTrudnosciEnum.SREDNIE){
                        _dostepnePytaniaLatwe.add(model);
                    }
                    if(model.poziom == PoziomTrudnosciEnum.TRUDNE){
                        _dostepnePytaniaLatwe.add(model);
                    }
                }
            }
        }
    }
    fun DajPytanie(poziom: PoziomTrudnosciEnum): PytanieModel
    {
        if (poziom == PoziomTrudnosciEnum.LATWE){
            val randomIndex = (0 until _dostepnePytaniaLatwe.size).random()
            return _dostepnePytaniaLatwe.removeAt(randomIndex);
        }
        if (poziom == PoziomTrudnosciEnum.SREDNIE){
            val randomIndex = (0 until _dostepnePytaniaSrednie.size).random()
            return _dostepnePytaniaSrednie.removeAt(randomIndex);
        }
        if (poziom == PoziomTrudnosciEnum.LATWE){
            val randomIndex = (0 until _dostepnePytaniaTrudne.size).random()
            return _dostepnePytaniaTrudne.removeAt(randomIndex);
        }
        else
        {
            throw IllegalArgumentException();
        }
    }

    private fun mapPytanieEntityToModel(entity: PytanieEntity, poziomy: List<PoziomTrudnosciEntity>): PytanieModel {
        var poziom = poziomy.find { it.id == entity.poziomTrudnosciId }!!;

        return PytanieModel(
            id = entity.id,
            tresc = entity.tresc,
            odpATresc = entity.odpATresc,
            odpBTresc = entity.odpBTresc,
            odpCTresc = entity.odpCTresc,
            odpDTresc = entity.odpDTresc,
            poprawna = mapPoprawnaEntityToModel(entity.poprawna),
            poziom = mapPoziomEntityToModel(poziom)
        );
    }

    private fun mapPoziomEntityToModel(poziom: PoziomTrudnosciEntity) : PoziomTrudnosciEnum
    {
        if(poziom.poziomTrudnosci == "latwe")
            return PoziomTrudnosciEnum.LATWE
        if(poziom.poziomTrudnosci == "srednie")
            return PoziomTrudnosciEnum.SREDNIE
        if(poziom.poziomTrudnosci == "trudne")
            return PoziomTrudnosciEnum.TRUDNE
        
        throw Exception("mapPoziomEntityToModel failed");
    }

    private fun mapPoprawnaEntityToModel(poprawna: Char) : PoprawnaOdpEnum
    {
        if(poprawna == 'A')
            return PoprawnaOdpEnum.A;
        if(poprawna == 'B')
            return PoprawnaOdpEnum.B;
        if(poprawna == 'C')
            return PoprawnaOdpEnum.C;
        if(poprawna == 'D')
            return PoprawnaOdpEnum.D;

        throw Exception("mapPoprawnaEntityToModel failed");
    }
}