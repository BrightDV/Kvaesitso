package de.mm20.launcher2.database

import android.content.ComponentName
import androidx.lifecycle.LiveData
import androidx.room.*
import de.mm20.launcher2.database.entities.IconEntity
import de.mm20.launcher2.database.entities.IconPackEntity

@Dao
interface IconDao {
    @Insert
    fun insertAll(icons: List<IconEntity>)

    @Query("SELECT drawable FROM Icons WHERE componentName = :componentName AND iconPack = :iconPack")
    suspend fun getIconName(componentName: String, iconPack: String): String?

    @Query("SELECT * FROM Icons WHERE componentName = :componentName AND iconPack = :iconPack")
    suspend fun getIcon(componentName: String, iconPack: String): IconEntity?

    @Query("DELETE FROM Icons WHERE iconPack = :iconPack")
    fun deleteIcons(iconPack: String)

    @Transaction
    fun installIconPack(iconPack: IconPackEntity, icons: List<IconEntity>) {
        deleteIconPack(iconPack)
        deleteIcons(iconPack.packageName)
        insertAll(icons)
        installIconPack(iconPack)
    }

    @Transaction
    fun installGrayscaleIconMap(packageName: String, icons: List<IconEntity>) {
        deleteIcons(packageName)
        insertAll(icons)
    }

    @Insert
    fun installIconPack(iconPack: IconPackEntity)

    @Query("SELECT * FROM IconPack")
    suspend fun getInstalledIconPacks(): List<IconPackEntity>

    @Query("SELECT * FROM IconPack")
    fun getInstalledIconPacksLiveData(): LiveData<List<IconPackEntity>>

    @Delete
    fun deleteIconPack(iconPack: IconPackEntity)

    @Query("SELECT * FROM IconPack WHERE packageName = :packageName AND version = :version")
    suspend fun getPacks(packageName: String, version: String): List<IconPackEntity>

    @Transaction
    suspend fun isInstalled(iconPack: IconPackEntity): Boolean {
        return getPacks(iconPack.packageName, iconPack.version).isNotEmpty()
    }

    @Query("DELETE FROM Icons WHERE iconPack NOT IN (:packs)")
    fun deleteAllIconsExcept(packs: List<String>)

    @Query("DELETE FROM IconPack WHERE packageName NOT IN (:packs)")
    fun deleteAllPacksExcept(packs: List<String>)

    @Transaction
    fun uninstallIconPacksExcept(packs: List<String>) {
        deleteAllIconsExcept(packs)
        deleteAllPacksExcept(packs)
    }

    @Query("SELECT drawable FROM Icons WHERE iconPack = :pack AND type = 'iconback'")
    suspend fun getIconBacks(pack: String): List<String>

    @Query("SELECT drawable FROM Icons WHERE iconPack = :pack AND type = 'iconupon'")
    suspend fun getIconUpons(pack: String): List<String>

    @Query("SELECT drawable FROM Icons WHERE iconPack = :pack AND type = 'iconmask'")
    suspend fun getIconMasks(pack: String): List<String>

    @Query("SELECT scale FROM IconPack WHERE packageName = :pack")
    suspend fun getScale(pack: String): Float?

    @Query("SELECT * FROM Icons WHERE type = 'greyscale_icon' AND componentName = :componentName")
    suspend fun getGreyscaleIcon(componentName: String): IconEntity?
}