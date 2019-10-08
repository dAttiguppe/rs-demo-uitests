@UITests
Feature: To test E2E UI tests and filter tests on RS Components website

  @SearchByMenu
  Scenario: To checkout a random product from Main Menu section
    Given the user selects Power from AllProducts section
    When the user adds the product to the basket
    And clicks on Checkout securely
    Then the product should appear on the checkout page

  @SearchByField
  Scenario: To search for a product and checkout by brand or stock number
    Given the user searches for Logitech product
    When the user adds the product to the basket
    And clicks on Checkout securely
    Then the product should appear on the checkout page

  @AddToCompareSectionUsingFilters
  Scenario: To filter by a random category and add products to compare section
    Given the user selects SemiConductors from AllProducts section
    When the user selects filters section
      | filterType | numberOfFilters | subFilter  |
#    Chooses a random available filter and lets the user choose the number of sub filters
      | Random      |   1              | 1         |
    And the user adds the product to the compare section

  @FilterSelectionUsingFilters
  Scenario: To filter by specific filter category and choose multiple filters
      Given the user selects Connectors from AllProducts section
      When the user selects filters section
         | filterType | numberOfFilters | subFilter  |
    #    If the filter type is included - numberOfFilters column needs to have a value of 1 always
         | Brand      |   1              | 1         |
        Then the filtered results should display

    @SwitchToGridViewUsingFilters
    Scenario: To filter by specific filter category and switch to grid view
      Given the user selects Connectors from AllProducts section
      When the user selects filters section
        | filterType | numberOfFilters | subFilter  |
        | Random      |   1              | 1         |
      And the user switched to grid view
      Then the results should be displayed in a grid








