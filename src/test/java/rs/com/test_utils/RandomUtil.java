package rs.com.test_utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    public static int generateRandomNumInSpecifiedRange(int start, int end) {
        return ThreadLocalRandom.current().nextInt(start, end);
    }

    public static long generateRandomNumInSpecifiedRange(long start, long end) {
        return ThreadLocalRandom.current().nextLong(start, end);
    }

    public static LocalDate generateSeasonStartDate(LocalDate startDate, LocalDate endDate) {
        long randomDays = generateRandomNumInSpecifiedRange(0,(startDate.until(endDate, ChronoUnit.DAYS)));
        return startDate.plusDays(randomDays);
    }

    public static int generateRandomCollectionId(int arraySize) {
        return generateRandomNumInSpecifiedRange(0, arraySize);
    }

    public static ArrayList<Integer> generateRandomArrayList(int randomNumbersCount, int arraySize)
    {
        ArrayList<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < randomNumbersCount; i++)
        {
            list.add(random.nextInt(arraySize));
        }
        list.stream().forEach(p ->System.out.println("Random number list-----"+p));
        Collections.shuffle(list);
        return list;
    }

    public static String getRandomAdultDate() {
        //changed the Adult age from 19 to 20 to suit USA where the maximum child age is 19
        return getRandomDate(20, 65);
    }

//    public static String getRandomChildDate(int passengerId, int numberOfRoom, InputData inputData, LocalDate endArrivalDate) {
//        List<List<Integer>> childrenAges = inputData.getRoomsChildrenAges();
//        List<Integer> adultCount = inputData.getRoomsAdultsCount();
//        int childPosition = (passengerId-adultCount.get(numberOfRoom-1))-1;
//        Integer childAge = childrenAges.get(numberOfRoom-1).get(childPosition);
//        LocalDate childDOB = endArrivalDate.minusYears(childAge).minusMonths(3);
//        return childDOB.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//    }

    public static String getRandomInfantDate() {
        int randomDay = getRandomNextInt(365) + 1;
        LocalDate date = LocalDate.now();
        LocalDate dateRandom = date.minusDays(randomDay);

        return DateConstants.DATE_TIME_FORMATTER_DD_MM_YYYY.format(dateRandom);
    }

    private static String getRandomDate(int minYears, int maxYears) {
        LocalDate date = LocalDate.now();

        int endYear = date.minusYears(minYears).getYear();
        int startYear = date.minusYears(maxYears).getYear();

        int randomYear = getRandomNextInt(endYear - startYear + 1) + startYear;// random.nextInt(endYear - startYear + 1) + startYear;
        int randomDay = getRandomNextInt(365) + 1;

        LocalDate dateRandom = LocalDate.ofYearDay(randomYear, randomDay);
        return DateConstants.DATE_TIME_FORMATTER_DD_MM_YYYY.format(dateRandom);
    }

    public static int getRandomNextInt(int number) {
        Random random = new Random();
        return random.nextInt(number);
    }
}