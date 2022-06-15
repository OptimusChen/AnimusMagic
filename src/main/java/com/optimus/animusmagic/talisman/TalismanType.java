package com.optimus.animusmagic.talisman;

public enum TalismanType {

    GENERIC(0, "An animus dragon can enchant generic talismans to give special buffs!", "&8Generic Talisman",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTdlODk3OGM2NjI0ZDc5ZjE4YmJmZTY1ZDVkZGZiYmM5MTQ1MDE1ODBjNDM1YzUxYTc4OTY2MTk0NjQ0MWU2OSJ9fX0=", 0),
    RUNNING(4, "Putting this talisman in an active talisman slot will grant you +%level0% speed!",
            "&bRunning Talisman",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjljMTQ5Mzc5YmZmMzcxNGYwYTE0NmE0OTc1YTkyMzE1NmUxZDU1NjMyOGVkYjg5MjEzNTE4MmJhM2FhM2M4MyJ9fX0=", 10),
    SHIELD(5, "Putting this talisman in an active talisman slot will grant you +%level armor points!",
            "&8Shield Talisman",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGE1Yzg2ODY0MTFkNDQ2YzkwYzE5MWM5M2Y4MGI5ZmZiMWNkMjQ3YWExMmEyMjZmODk3OTk4MWFkNDM4OGJlZSJ9fX0",
            10),
    STRENGTH(3, "Putting this talisman in an active talisman slot will grant you +%halflevel0% attack boost!",
            "&4Strength Talisman",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2IwMzNiMmMyZDNjZjU4OTEzM2FiNzMwZWQxYThiNDQzMTNkNjI5OTU0ODBjM2EwZGFjMzI4ZDUzN2UyN2Q3ZiJ9fX0",
            10),
    GLIDING(1, "Putting this talisman in an active talisman slot will grant you permanent slow-falling!",
            "&fGliding Talisman",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzZmMzNiMjk3YTk4MWI1MjdiZWMzOTMxNjg0MDFkOGEyZWNhZGViOWYxNjAzYmE1ZTI3NmY0MmQ2NDQ3NTExNiJ9fX0",
            5),
    STEALTH(1, "Putting this talisman in an active talisman slot will grant you permanent invisibility!",
            "&8Stealth Talisman",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2IxZTAxN2I1ODQxYjk4NTc3YTJiOGVkOWJmMDIzZDNiZjE0OWQ3ZWY2Y2RkY2VmY2FkZjdiNGIyN2MzMWIzMSJ9fX0",
            10),
    GLOWING(1, "Putting this talisman in an active talisman slot will grant you the glowing effect!",
            "&eGlowing Talisman",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q5ZDE5NWYwOTJlNDM1MDViNTQ5OWU3MzJkY2RiOWU4NTIwNjlkNWFkMzVjMTE0MzJjOTkwYWZjZmU2NDAzNyJ9fX0",
            5);

    int maxLevel;
    int mana;
    String lore;
    String name;
    String headId;

    TalismanType(int maxLevel, String lore, String name, String headId, int mana) {
        this.maxLevel = maxLevel;
        this.lore = lore;
        this.mana = mana;
        this.name = name;
        this.headId = headId;
    }

    public int getMaxLevel() { return maxLevel; }

    public int getMana() { return mana; }

    public String getLore() { return lore; }

    public String getName() { return name; }

    public String getHeadId() { return headId; }

    public boolean isLeveled() { return maxLevel > 1; }

}
