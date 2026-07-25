package net.hypixel.orangejuice.service.generator;

import net.aerh.imagegenerator.item.GeneratedObject;
import org.jetbrains.annotations.Nullable;

public class SingleDialogueService extends GeneratorService {

    public static GeneratedObject generate(
        String npcName,
        String[] dialogue,
        @Nullable Integer maxLineLength,
        @Nullable Boolean abiphone,
        @Nullable String skinValue,
        @Nullable String texturePack
    ) {
        abiphone = abiphone != null && abiphone;
        maxLineLength = maxLineLength == null ? 91 : maxLineLength;

        for (int i = 0; i < dialogue.length; i++) {
            dialogue[i] = "&e[NPC] " + npcName + "&f: " + (abiphone ? "&b%%ABIPHONE%%&f " : "") + dialogue[i];
            String line = dialogue[i];

            if (line.contains("{options:")) { // TODO: make a better way to check for options (probably just another json object)
                String[] split = line.split("\\{options: ?");
                dialogue[i] = split[0];
                String[] options = split[1].replace("}", "").split(", ");
                dialogue[i] += "\n&eSelect an option: &f";
                for (String option : options) {
                    dialogue[i] += "&a" + option + "&f ";
                }
            }
        }

        return internalGenerateDialogue(dialogue, maxLineLength, skinValue, texturePack);
    }
}