package com.ddevs.linkit

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat


class SharingShortcutsManager {

    /**
     * Category name defined in res/xml/shortcuts.xml that accepts data of type text/plain
     * and will trigger [SendMessageActivity]
     */
    val categoryTextShareTarget = "com.ddevs.linkit.category.TEXT_SHARE_TARGET"

    /**
     * Define maximum number of shortcuts.
     * Don't add more than [ShortcutManagerCompat.getMaxShortcutCountPerActivity].
     */
    private val maxShortcuts = 2

    /**
     * Publish the list of dynamic shortcuts that will be used in Direct Share.
     * <p>
     * For each shortcut, we specify the categories that it will be associated to
     * the intent that will trigger when opened as a static launcher shortcut,
     * and the Shortcut ID between other things.
     * <p>
     * The Shortcut ID that we specify in the [ShortcutInfoCompat.Builder] constructor will
     * be received in the intent as [Intent.EXTRA_SHORTCUT_ID].
     * <p>
     * In this code sample, this method is completely static. We are always setting the same sharing
     * shortcuts. In a real-world example, we would replace existing shortcuts depending on
     * how the user interacts with the app as often as we want to.
     */
    fun pushDirectShareTargets(context: Context) {
        val shortcuts = ArrayList<ShortcutInfoCompat>()

        // Category that our sharing shortcuts will be assigned to
        val contactCategories = setOf(categoryTextShareTarget)

        // Adding maximum number of shortcuts to the list


        // Item that will be sent if the shortcut is opened as a static launcher shortcut
        val staticLauncherShortcutIntent1 = Intent(Intent.ACTION_DEFAULT)
        val staticLauncherShortcutIntent2 = Intent(Intent.ACTION_DEFAULT)
        // Creates a new Sharing Shortcut and adds it to the list
        // The id passed in the constructor will become EXTRA_SHORTCUT_ID in the received Intent
        shortcuts.add(
            ShortcutInfoCompat.Builder(context, "Share")
                .setShortLabel("Share Linkit")
                // Icon that will be displayed in the share target
                .setIntent(staticLauncherShortcutIntent1)
                .setIcon(IconCompat.createWithResource(context, R.drawable.ic_baseline_share_24))
                // Make this sharing shortcut cached by the system
                // Even if it is unpublished, it can still appear on the sharesheet
                .setLongLived(true)
                .setCategories(contactCategories)
                // Person objects are used to give better suggestion
                .build()
        )
        shortcuts.add(
            ShortcutInfoCompat.Builder(context, "Add")
                .setShortLabel("Add to Linkit")
                // Icon that will be displayed in the share target
                .setIntent(staticLauncherShortcutIntent2)
                .setIcon(IconCompat.createWithResource(context, R.drawable.ic_baseline_add_24))
                // Make this sharing shortcut cached by the system
                // Even if it is unpublished, it can still appear on the sharesheet
                .setLongLived(true)
                .setCategories(contactCategories)
                // Person objects are used to give better suggestion
                .build()
        )


        ShortcutManagerCompat.addDynamicShortcuts(context, shortcuts)
    }

    /**
     * Remove all dynamic shortcuts
     */
    fun removeAllDirectShareTargets(context: Context) {
        ShortcutManagerCompat.removeAllDynamicShortcuts(context)
    }
}


