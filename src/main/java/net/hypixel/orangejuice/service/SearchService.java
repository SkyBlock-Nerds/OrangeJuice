package net.hypixel.orangejuice.service;

import net.aerh.imagegenerator.data.*;
import net.aerh.imagegenerator.impl.tooltip.MinecraftTooltipGenerator;
import net.aerh.imagegenerator.pack.PackId;
import net.aerh.imagegenerator.pack.PackRepository;
import net.aerh.imagegenerator.spritesheet.Spritesheet;
import net.hypixel.orangejuice.util.StringUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class SearchService {
    // TODO run is null or blank check for searchterms not in the filter loops because bad of performance ofc aka copy tooltipStyle in code flow

    public static List<String> itemNames(@Nullable String searchTerm, @Nullable String packId) {
        // TODO add logic for adding pack items to the returned list and fetching id's

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

    public static List<Flavor> Flavor(@Nullable String searchTerm) {
        return Flavor.getFlavors()
            .stream()
            .filter(i -> StringUtil.isNullOrBlank(searchTerm) || i.getName().toLowerCase().contains(searchTerm.toLowerCase()))
            .toList();
    }

    public static List<PackId> texturePacks(@Nullable String searchTerm) {
        return PackRepository.global().registeredPacks()
            .stream()
            .filter(p -> StringUtil.isNullOrBlank(searchTerm) || p.name().toLowerCase().contains(searchTerm.toLowerCase()) || p.namespace().toLowerCase().contains(searchTerm.toLowerCase()))
            .toList();
    }

    public static List<String> tooltipStyle(@Nullable String packId, @Nullable String searchTerm) {
        if (StringUtil.isNullOrBlank(packId))
            return List.of();

        packId = packId.toLowerCase();
        if (packId.equals("minecraft:minecraft") || packId.equals("vanilla"))
            return List.of();

        if (searchTerm == null)
            return PackRepository.global().tooltipStyles(PackId.parse(packId));

        String finalSearchTerm = searchTerm.toLowerCase();
        return PackRepository.global().tooltipStyles(PackId.parse(packId))
            .stream()
            .filter(ts -> ts.contains(finalSearchTerm))
            .toList();
    }
}