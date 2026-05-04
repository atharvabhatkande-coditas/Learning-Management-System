package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
}
