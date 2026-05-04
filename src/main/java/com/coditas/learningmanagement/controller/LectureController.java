package com.coditas.learningmanagement.controller;

import com.coditas.learningmanagement.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lms/v1/lecture")
public class LectureController {

    private final LectureService lectureService;
}
