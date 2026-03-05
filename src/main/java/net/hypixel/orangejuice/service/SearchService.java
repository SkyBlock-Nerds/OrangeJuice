package net.hypixel.orangejuice.service;

import net.aerh.imagegenerator.data.Gemstone;
import net.aerh.imagegenerator.data.Icon;
import net.aerh.imagegenerator.data.Rarity;
import net.aerh.imagegenerator.data.Stat;
import net.aerh.imagegenerator.impl.tooltip.MinecraftTooltipGenerator;
import net.aerh.imagegenerator.spritesheet.Spritesheet;
import net.hypixel.orangejuice.util.StringUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class SearchService {
    public static List<String> itemNames(@Nullable String searchTerm) {
        return Spritesheet.getImageMap()
            .keySet()
            .stream()
            .filter(s -> StringUtil.isNullOrBlank(searchTerm) || s.toLowerCase().contains(searchTerm.toLowerCase()))
            .toList();
    }

    public static List<String> itemRarities(@Nullable String searchTerm) {
        return Rarity.getRarityNames()
            .stream()
            .filter(s -> StringUtil.isNullOrBlank(searchTerm) || s.toLowerCase().contains(searchTerm.toLowerCase()))
            .toList();
    }

    public static List<Icon> icons(@Nullable String searchTerm) {
        return Icon.getIcons()
            .stream()
            .filter(i -> StringUtil.isNullOrBlank(searchTerm) || i.getName().toLowerCase().contains(searchTerm.toLowerCase()))
            .toList();
    }

    public static List<Stat> stats(@Nullable String searchTerm) {
        return Stat.getStats()
            .stream()
            .filter(i -> StringUtil.isNullOrBlank(searchTerm) || i.getName().toLowerCase().contains(searchTerm.toLowerCase()))
            .toList();
    }

    public static List<Gemstone> gemstones(@Nullable String searchTerm) {
        return Gemstone.getGemstones()
            .stream()
            .filter(i -> StringUtil.isNullOrBlank(searchTerm) || i.getName().toLowerCase().contains(searchTerm.toLowerCase()))
            .toList();
    }

    public static List<String> tooltipSide(@Nullable String searchTerm) {
        return Arrays.stream(MinecraftTooltipGenerator.TooltipSide.values())
            .map(MinecraftTooltipGenerator.TooltipSide::name)
            .filter(s -> StringUtil.isNullOrBlank(searchTerm) || s.toLowerCase().contains(searchTerm.toLowerCase()))
            .toList();
    }
}