package net.hypixel.orangejuice.service;

import net.aerh.imagegenerator.data.*;
import net.aerh.imagegenerator.impl.tooltip.MinecraftTooltipGenerator;
import net.aerh.imagegenerator.pack.PackId;
import net.aerh.imagegenerator.pack.PackRepository;
import net.aerh.imagegenerator.spritesheet.Spritesheet;
import net.hypixel.orangejuice.util.StringUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchService {
    public static List<String> itemNames(@Nullable String searchTerm, @Nullable String packId) {
        List<String> unfiltered = new ArrayList<>(Spritesheet.getImageMap().keySet());
        if (!StringUtil.isNullOrBlank(packId) && !packId.equals(PackId.VANILLA.toString())) {
            unfiltered.addAll(PackRepository.global().itemRefs(PackId.parse(packId)));
        }
        if (StringUtil.isNullOrBlank(searchTerm))
            return new ArrayList<>(unfiltered);
        @Nullable String finalSearchTerm = searchTerm.toLowerCase();
        return unfiltered.stream()
            .filter(s -> s.toLowerCase().contains(finalSearchTerm))
            .toList();
    }

    public static List<String> itemRarities(@Nullable String searchTerm) {
        var unfiltered = Rarity.getRarityNames();
        if (StringUtil.isNullOrBlank(searchTerm))
            return unfiltered;
        @Nullable String finalSearchTerm = searchTerm.toLowerCase();
        return unfiltered.stream()
            .filter(s -> s.toLowerCase().contains(finalSearchTerm))
            .toList();
    }

    public static List<Icon> icons(@Nullable String searchTerm) {
        var unfiltered = Icon.getIcons();
        if (StringUtil.isNullOrBlank(searchTerm))
            return unfiltered;
        @Nullable String finalSearchTerm = searchTerm.toLowerCase();
        return unfiltered.stream()
            .filter(i -> i.getName().toLowerCase().contains(finalSearchTerm))
            .toList();
    }

    public static List<Stat> stats(@Nullable String searchTerm) {
        var unfiltered = Stat.getStats();
        if (StringUtil.isNullOrBlank(searchTerm))
            return unfiltered;
        @Nullable String finalSearchTerm = searchTerm.toLowerCase();
        return unfiltered.stream()
            .filter(i -> i.getName().toLowerCase().contains(finalSearchTerm))
            .toList();
    }

    public static List<Gemstone> gemstones(@Nullable String searchTerm) {
        var unfiltered = Gemstone.getGemstones();
        if (StringUtil.isNullOrBlank(searchTerm))
            return unfiltered;
        @Nullable String finalSearchTerm = searchTerm.toLowerCase();
        return unfiltered.stream()
            .filter(i -> i.getName().toLowerCase().contains(finalSearchTerm))
            .toList();
    }

    public static List<String> tooltipSide(@Nullable String searchTerm) {
        var unfiltered = Arrays.stream(MinecraftTooltipGenerator.TooltipSide.values())
            .map(MinecraftTooltipGenerator.TooltipSide::name);
        if (StringUtil.isNullOrBlank(searchTerm))
            return unfiltered.toList();
        @Nullable String finalSearchTerm = searchTerm.toLowerCase();
        return unfiltered.filter(s -> s.toLowerCase().contains(finalSearchTerm))
            .toList();
    }

    public static List<Flavor> Flavor(@Nullable String searchTerm) {
        var unfiltered = Flavor.getFlavors();
        if (StringUtil.isNullOrBlank(searchTerm))
            return unfiltered;
        @Nullable String finalSearchTerm = searchTerm.toLowerCase();
        return unfiltered.stream()
            .filter(i -> i.getName().toLowerCase().contains(finalSearchTerm))
            .toList();
    }

    public static List<PackId> texturePack(@Nullable String searchTerm) {

        var unfiltered = new ArrayList<>(PackRepository.global().registeredPacks());
        unfiltered.add(PackId.VANILLA);
        if (StringUtil.isNullOrBlank(searchTerm))
            return unfiltered;
        @Nullable String finalSearchTerm = searchTerm.toLowerCase();
        return unfiltered.stream()
            .filter(p -> p.toString().toLowerCase().contains(finalSearchTerm))
            .toList();
    }

    public static List<String> tooltipStyle(@Nullable String packId, @Nullable String searchTerm) {
        if (StringUtil.isNullOrBlank(packId))
            return List.of();

        packId = packId.toLowerCase();
        if (packId.equals("minecraft:minecraft") || packId.equals("vanilla"))
            return List.of();

        var unfiltered = PackRepository.global().tooltipStyles(PackId.parse(packId));
        if (searchTerm == null)
            return unfiltered;
        String finalSearchTerm = searchTerm.toLowerCase();
        return unfiltered.stream()
            .filter(ts -> ts.contains(finalSearchTerm))
            .toList();
    }
}
