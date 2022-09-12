package io.jjong.security.controller;

import io.jjong.security.student.StudentTokenProvider;
import io.jjong.security.teacher.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final StudentTokenProvider provider;

    @PreAuthorize("hasAnyAuthority('ROLE_TEACHER')")
    @GetMapping("/main")
    public String main(){
        return "TeacherMain";
    }


    @PreAuthorize("hasAnyAuthority('ROLE_TEACHER')")
    @GetMapping("/students")
    public String findStudents(@AuthenticationPrincipal Teacher teacher, Model model) {
        model.addAttribute("students", provider.myStudents(teacher.getId()));
        return "TeacherMain";
    }


}
