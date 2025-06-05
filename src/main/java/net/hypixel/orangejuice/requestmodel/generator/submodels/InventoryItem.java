package net.hypixel.orangejuice.requestmodel.generator.submodels;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.hypixel.orangejuice.util.Util;

import java.util.List;

import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.ITEM_ID_DESCRIPTION;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.ITEM_ID_EXAMPLE;

@Data
public class InventoryItem {

    @Schema(description = ITEM_ID_DESCRIPTION, example = ITEM_ID_EXAMPLE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String itemId;

    @Schema(description = "Where the item should be located in the inventory", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ItemLocation> locations;

    @Schema(description = "The amount of the item", example = "3", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer amount;

    @Schema(description = "Extra attributes for the item", example = "[\"enchanted\", \"hover\"]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String[] extraData;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(itemId);

        if (extraData != null) {
            for (String data : extraData) {
                if (!Util.isNullOrBlank(data)) {
                    result.append(',').append(data);
                }
            }
        }

        result.append(":[");
        if (locations.size() == 1) {
            result.append(locations.getFirst().toString());
        } else if (locations.size() >= 2) {
            result.append(locations.getFirst().toString());
            for (int i = 1; i < locations.size(); i++) {
                result.append(',').append(locations.get(i).toString());
            }
        } else {
            throw new IllegalArgumentException("Invalid location: " + locations.toString());
        }
        result.append("]");

        if (amount > 0) { // Only appended when an input is given (default int in java is 0) (doesn't reeeaaaly matter but just gives me piece of mind)
            result.append(':').append(amount);
        }

        return result.toString();
    }

    public static String toStringFromArray(InventoryItem[] items) {
        if (items == null || items.length == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        result.append(items[0].toString());
        for (int i = 1; i < items.length; i++) {
            result.append("%%").append(items[i].toString());
        }

        return result.toString();
    }
}