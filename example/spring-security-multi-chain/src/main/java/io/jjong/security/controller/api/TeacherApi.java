package io.jjong.security.controller.api;

import io.jjong.security.student.Student;
import io.jjong.security.student.StudentTokenProvider;
import io.jjong.security.teacher.Teacher;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherApi {

    private final StudentTokenProvider provider;


    @PreAuthorize("hasAnyAuthority('ROLE_TEACHER')")
    @GetMapping("/students")
    public List<Student> findStudents(@AuthenticationPrincipal Teacher teacher) {
        return provider.myStudents(teacher.getId());
    }


}
