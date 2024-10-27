package Course;

import Command.Command;

public class CourseTime {
    private int day;
    private int start;
    private int end;

    public CourseTime() {
        this.day = 0;
        this.start = 0;
        this.end = 0;
    }

    public CourseTime(int day, int start, int end) {
        this.day = day;
        this.start = start;
        this.end = end;
    }

    public int getDay() {
        return this.day;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public String toString() {
        return String.format("%d_%d-%d", this.day, this.start, this.end);
    }

    public void setCourseTime(String time) throws IllegalArgumentException {
        String[] timeArr = time.split("[_\\-]");
        try {
            this.day = Integer.parseInt(timeArr[0]);
            this.start = Integer.parseInt(timeArr[1]);
            this.end = Integer.parseInt(timeArr[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(Command.ERR_MSG[18]);
        }
        if (this.day < 1 || this.day > 7 || this.start < 1 || this.start > 14
                || this.end < 1 || this.end > 14 || this.start > this.end) {
            throw new IllegalArgumentException(Command.ERR_MSG[18]);
        }
    }

    public boolean isConflict(CourseTime time) {
        if (this.day != time.day) {
            return false;
        } else return (this.start <= time.getEnd() && this.start >= time.getStart())
                || (this.end >= time.getStart() && this.end <= time.getEnd());
    }
}