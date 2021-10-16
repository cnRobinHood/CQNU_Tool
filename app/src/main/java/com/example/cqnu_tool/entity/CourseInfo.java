package com.example.cqnu_tool.entity;

import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleEnable;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class CourseInfo extends LitePalSupport implements ScheduleEnable {
    String courseName;
    String courseLength;
    String courseAddress;
    String courseWeek;
    String teacherName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseLength() {
        return courseLength;
    }

    public void setCourseLength(String courseLength) {
        this.courseLength = courseLength;
    }

    public String getCourseAddress() {
        return courseAddress;
    }

    public void setCourseAddress(String courseAddress) {
        this.courseAddress = courseAddress;
    }

    public String getCourseWeek() {
        return courseWeek;
    }

    public void setCourseWeek(String courseWeek) {
        this.courseWeek = courseWeek;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public Schedule getSchedule() {
        Schedule schedule=new Schedule();
        schedule.setDay(Integer.parseInt(getCourseWeek()));
        schedule.setName(getCourseName());
        schedule.setRoom(getCourseAddress());
        schedule.setStart(Integer.parseInt(getCourseLength().substring(0,1)));
        schedule.setStep(Integer.parseInt(getCourseLength().substring(2,3))-Integer.parseInt(getCourseLength().substring(0,1))+1);
        schedule.setTeacher(getTeacherName());
        List<Integer> list= new ArrayList<>();
        for(int i=1;i<=20;i++) list.add(i);
        schedule.setWeekList(list);
        return schedule;
    }
}
