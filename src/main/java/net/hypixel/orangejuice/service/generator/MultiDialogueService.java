package net.hypixel.orangejuice.service.generator;

import net.aerh.imagegenerator.exception.GeneratorException;
import net.aerh.imagegenerator.image.GeneratorImageBuilder;
import net.aerh.imagegenerator.image.MinecraftTooltip;
import net.aerh.imagegenerator.impl.MinecraftPlayerHeadGenerator;
import net.aerh.imagegenerator.impl.tooltip.MinecraftTooltipGenerator;
import net.aerh.imagegenerator.item.GeneratedObject;
import net.aerh.imagegenerator.pack.PackId;
import net.hypixel.orangejuice.requestmodel.generator.submodels.MultiDialogueLine;
import net.hypixel.orangejuice.util.StringUtil;
import org.jetbrains.annotations.Nullable;

public class MultiDialogueService extends GeneratorService {

    public static GeneratedObject generate(
        String[] npcNames,
        MultiDialogueLine[] dialogue,
        @Nullable Integer maxLineLength,
        @Nullable Boolean abiphone,
        @Nullable String skinValue,
        @Nullable String texturePack
    ) {
        abiphone = abiphone != null && abiphone;
        maxLineLength = maxLineLength == null ? 91 : maxLineLength;

        String[] lines = new String[dialogue.length];

        for (int i = 0; i < dialogue.length; i++) {
            try {
                lines[i] = "&e[NPC] " + npcNames[dialogue[i].getNpcIndex()] + "&f: " + (abiphone ? "&b%%ABIPHONE%%&f " : "") + dialogue[i].getLine();
                String line = lines[i];

                if (line.contains("{options:")) { // TODO: make a better way to check for options (probably just another json object)
                    String[] split2 = line.split("\\{options: ?");
                    lines[i] = split2[0];
                    String[] options = split2[1].replace("}", "").split(", ?");
                    lines[i] += "\n&eSelect an option: &f";
                    for (String option : options) {
                        lines[i] += "&a" + option + "&f ";
                    }
                }
            } catch (NumberFormatException exception) {
                throw new GeneratorException("Invalid NPC name index found in dialogue: " + dialogue[i].getNpcIndex() + " (line " + (i + 1) + ")");
            }
        }

        return internalGenerateDialogue(lines, maxLineLength, skinValue, texturePack);
    }
}
