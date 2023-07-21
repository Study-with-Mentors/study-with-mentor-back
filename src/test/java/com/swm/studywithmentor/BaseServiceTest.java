package com.swm.studywithmentor;

import com.swm.studywithmentor.repository.ActivityRepository;
import com.swm.studywithmentor.repository.ClazzRepository;
import com.swm.studywithmentor.repository.CourseRepository;
import com.swm.studywithmentor.repository.EnrollmentRepository;
import com.swm.studywithmentor.repository.FieldRepository;
import com.swm.studywithmentor.repository.ImageRepository;
import com.swm.studywithmentor.repository.InvoiceRepository;
import com.swm.studywithmentor.repository.LessonRepository;
import com.swm.studywithmentor.repository.MentorRepository;
import com.swm.studywithmentor.repository.SessionRepository;
import com.swm.studywithmentor.repository.StudentRepository;
import com.swm.studywithmentor.repository.UserRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("deploy")
public abstract class BaseServiceTest {
    @MockBean
    protected ActivityRepository activityRepository;
    @MockBean
    protected ClazzRepository clazzRepository;
    @MockBean
    protected CourseRepository courseRepository;
    @MockBean
    protected EnrollmentRepository enrollmentRepository;
    @MockBean
    protected FieldRepository fieldRepository;
    @MockBean
    protected ImageRepository imageRepository;
    @MockBean
    protected InvoiceRepository invoiceRepository;
    @MockBean
    protected LessonRepository lessonRepository;
    @MockBean
    protected MentorRepository mentorRepository;
    @MockBean
    protected SessionRepository sessionRepository;
    @MockBean
    protected StudentRepository studentRepository;
    @MockBean
    protected UserRepository userRepository;
}
