Feature: Fetching links from a Google search

    Fetching links from google

    Scenario Outline: First Five links
        Given Google search is loaded
        When I search for "<value>"
        Then There are at least <amount> links that result from it are saved
        Examples:
            | value   | amount |
            | Banana  | 5      |
            | Pera    | 7      |
            | Manzana | 2      |
            | Naranja | 1      |