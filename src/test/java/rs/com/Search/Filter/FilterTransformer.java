package rs.com.Search.Filter;

import cucumber.api.TypeRegistry;
import io.cucumber.datatable.DataTableType;

import java.util.Locale;
import java.util.Map;

public class FilterTransformer {
    public Locale locale() {
        return Locale.ENGLISH;
    }

    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        typeRegistry.defineDataTableType(new DataTableType(FilterSelection.class,
                        (Map<String, String> row) -> {
                            String filterSelection = row.get("filterSection");
                            Integer numberOfFilters = Integer.parseInt(row.get("numberOfFilters"));
                            Integer numberOfSubFilters = Integer.parseInt(row.get("numberOfSubFilters"));

                            return new FilterSelection(filterSelection, numberOfFilters, numberOfSubFilters);
                        }
                )
        );
    }

    public void configureTypeRegistry(String filterType, int numberOfFilters, int numberOfSubFilters) {
    }
}


