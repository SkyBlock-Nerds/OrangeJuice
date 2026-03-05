package net.hypixel.orangejuice.requestmodel.generator.submodels;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.hypixel.orangejuice.requestmodel.ApiDocsConstants;
import org.jetbrains.annotations.Nullable;

@Data
public class ItemLocation {

    /**
     * Depending on if only location1 is filled or location1 and location2 are filled it will either be a range of items or a single slot.
     */
    @Schema(description = ApiDocsConstants.LOCATION_DESCRIPTION, example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer location1;
    /**
     * Depending on if only location1 is filled or location1 and location2 are filled it will either be a range of items or a single slot.
     */
    @Nullable
    @Schema(description = ApiDocsConstants.LOCATION_DESCRIPTION + ", if this is left empty it will only fill the slot specified in location1", example = "2", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer location2;

    @Override
    public String toString() {
        if (location1 != null && location2 != null) {
            return location1.toString() + "-" + location2.toString();
        }
        else if (location1 != null) {
            return location1.toString();
        }
        else {
            throw new IllegalArgumentException("Both location1 and location2 are null, at least location1 one should contain a positive integer");
        }
    }
}