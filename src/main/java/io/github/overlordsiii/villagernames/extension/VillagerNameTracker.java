package io.github.overlordsiii.villagernames.extension;

import io.github.overlordsiii.villagernames.mixin.VillagerEntityMixin;
import net.minecraft.entity.passive.VillagerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents all the features of a villager's names
 *
 * The static methods should be used to avoid having ugly casting everywhere
 * by diverting it into one place
 *
 * @see io.github.overlordsiii.villagernames.mixin.VillagerEntityMixin for implementation
 * @see io.github.overlordsiii.villagernames.util.VillagerUtil (soon) for usages
 */
@SuppressWarnings({"unused", "deprecation"})
public interface VillagerNameTracker {
    /**
     * gets the villagers first name
     * @return the villagers very first name (or first name)
     */
    @Nullable String getFirstName();

    /**
     * Gets the villagers last names, if it has any
     * @return the number of lastnames
     */

    List<String> getLastNames();

    /**
     * Sets the villager's first name
     * @param name the new first name
     */

    void setFirstName(String name);

    /**
     * Removes the villager's last name
     * @param name the last name to remove
     */

    void removeLastName(String name);

    /**
     * Removes all last names
     */
    void clearLastNames();

    /**
     * Adds a last name
     * @param name the name to add
     */

    void addLastName(String name);

    /**
     * Gets the villager's profession name
     * @return the profession name, or null if the profession name is toggled off
     */
    @Nullable String getProfessionName();

    /**
     * Removes the profession name if present
     */
    void removeProfessionName();

    /**
     * Adds a new profession name to the villagers name
     * @param appendedProfession the name of the profession
     */

    void setProfessionName(String appendedProfession);

    /**
     * Removes the Child Tag
     */
    void removeChildName();

    /**
     * Adds the Child Tag if enabled
     */

    void addChildName();

    /**
     * Returns the full name of the villager
     * @return the fullName
     */
    @Nullable String getFullName();

    /**
     * Updates the full name. This should be called when any other method in this interface is referenced
     *
     * It is by default called internally by the method implementation
     *
     * @see VillagerEntityMixin for more information
     * @return the new full name that is calculated based on other names
     */
    String updateFullName();

    static @Nullable String getFirstName(VillagerEntity entity) {
        return ((VillagerNameTracker)entity).getFirstName();
    }

    static List<String> getLastNames(VillagerEntity entity) {
        return ((VillagerNameTracker)entity).getLastNames();
    }

    static void setFirstName(VillagerEntity entity, String name) {
        ((VillagerNameTracker)entity).setFirstName(name);
    }

    static void removeLastName(String name, VillagerEntity entity) {
        ((VillagerNameTracker)entity).removeLastName(name);
    }

    static void addLastName(VillagerEntity entity, String name) {
        ((VillagerNameTracker)entity).addLastName(name);
    }

    static @Nullable String getProfessionName(VillagerEntity entity) {
        return ((VillagerNameTracker)entity).getProfessionName();
    }

    static void removeProfessionName(VillagerEntity entity) {
        ((VillagerNameTracker)entity).removeProfessionName();
    }

    static void setProfessionName(String professionName, VillagerEntity entity) {
        ((VillagerNameTracker)entity).setProfessionName(professionName);
    }

    static void removeChildName(VillagerEntity entity) {
        ((VillagerNameTracker)entity).removeChildName();
    }

    static void addChildName(VillagerEntity entity) {
        ((VillagerNameTracker)entity).addChildName();
    }

    static @Nullable String getFullName(VillagerEntity entity) {
        return ((VillagerNameTracker)entity).getFullName();
    }

    static void clearLastNames(VillagerEntity entity) {
        ((VillagerNameTracker)entity).clearLastNames();
    }

    static @Nullable String updateFullName(VillagerEntity entity) {
        return ((VillagerNameTracker)entity).updateFullName();
    }
}
