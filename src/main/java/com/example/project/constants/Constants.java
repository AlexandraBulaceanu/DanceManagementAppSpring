package com.example.project.constants;

public class Constants {
    public static final String SCHEDULE_ERROR = "Ops, a class is not allowed to be scheduled in the past";

    public static final String AVAILABILITY_ERROR = "You can't book the studio, because of availability conflict";

    public static final String NOT_UNIQUE_ERROR = "There can't be two entries with the same %s!";

    public static final String NO_ENTITY_FOUND = "There is no %s with %s: %s!";

    public static final String RESERVATION_ERROR = "Reservations in the past are not allowed";

    public static final String INSTRUCTOR_DELETION_ERROR = "Removal of instructors is not allowed if they teach classes";

    public static final String STUDENTS_NOT_FOUND = "No students found with the name %s";

    public static final String CLASS_FULLY_BOOKED = "The desired class is fully booked";

}
