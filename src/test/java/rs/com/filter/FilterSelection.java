package rs.com.filter;

public class FilterSelection {

    private String filterSection;
    private Integer numberOfFilters;
    private Integer numberOfSubFilters;

    public String getFilterSection() {
        return filterSection;
    }

    public void setFilterSection(String filterSection) {
        this.filterSection = filterSection;
    }

    public Integer getNumberOfFilters() {
        return numberOfFilters;
    }

    public void setNumberOfFilters(Integer numberOfFilters) {
        this.numberOfFilters = numberOfFilters;
    }

    public Integer getNumberOfSubFilters() {
        return numberOfSubFilters;
    }

    public void setNumberOfSubFilters(Integer numberOfSubFilters) {
        this.numberOfSubFilters = numberOfSubFilters;
    }



    public FilterSelection(String filterSection, Integer numberOfFilters, Integer numberOfSubFilters) {
        this.filterSection = filterSection;
        this.numberOfFilters = numberOfFilters;
        this.numberOfSubFilters = numberOfSubFilters;
    }


}
