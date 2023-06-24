package com.swm.studywithmentor.util;

import com.swm.studywithmentor.model.dto.create.*;
import com.swm.studywithmentor.model.entity.*;
import com.swm.studywithmentor.model.dto.update.SessionUpdateDto;
import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import com.swm.studywithmentor.model.entity.invoice.Invoice;
import com.swm.studywithmentor.model.entity.user.Mentor;
import com.swm.studywithmentor.model.entity.user.Student;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.model.dto.*;
import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.session.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ApplicationMapper {
    private final ModelMapper mapper;

    @Autowired
    public ApplicationMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<EnrollmentDto> enrollmentToDto(List<Enrollment> enrollments) {
        return enrollments.stream().map(this::enrollmentToDto).toList();
    }

    public EnrollmentDto enrollmentToDto(Enrollment enrollment) {
        return mapper.typeMap(Enrollment.class, EnrollmentDto.class)
                .addMappings(mapping -> mapping.map(src -> src.getInvoice().getInvoiceId(), (destination, value) -> destination.setInvoice((UUID) value)))
                .map(enrollment);
    }

    public List<UserDto> userToDto(List<User> users) {
        return users.stream().map(this::userToDto).toList();
    }

    public UserDto userToDto(User user) {
        return mapper.map(user, UserDto.class);
    }

    public List<InvoiceDto> invoiceToDto(List<Invoice> invoices) {
        return invoices.stream().map(this::invoiceToDto).toList();
    }

    public InvoiceDto invoiceToDto(Invoice invoice) {
        return mapper.typeMap(Invoice.class, InvoiceDto.class)
                .map(invoice);
    }

    public List<Enrollment> enrollmentToEntity(List<EnrollmentDto> enrollmentDtos) {
        return enrollmentDtos.stream().map(this::enrollmentToEntity).toList();
    }

    public void enrollmentToEntity(Enrollment enrollment, EnrollmentDto enrollmentDto) {
        mapper.map(enrollmentDto, enrollment);
    }

    public Enrollment enrollmentToEntity(EnrollmentDto enrollmentDto) {
        return mapper.map(enrollmentDto, Enrollment.class);
    }

    public List<User> userToEntity(List<UserDto> userDtos) {
        return userDtos.stream().map(this::userToEntity).toList();
    }

    public User userToEntity(UserDto user) {
        return mapper.map(user, User.class);
    }

    public List<Invoice> invoiceToEntity(List<InvoiceDto> invoices) {
        return invoices.stream().map(this::invoiceToEntity).toList();
    }

    public Invoice invoiceToEntity(InvoiceDto invoice) {
        return mapper.typeMap(InvoiceDto.class, Invoice.class)
                .addMappings(mapper -> mapper.skip(Invoice::setTotalPrice))
                .map(invoice);
    }

    public void invoiceToEntity(Invoice invoice, InvoiceDto invoiceDto) {
        mapper.map(invoiceDto, invoice);
    }

    public CourseDto toDto(Course course) {
        CourseDto courseDto = mapper.map(course, CourseDto.class);
        courseDto.setField(toDto(course.getField()));
        courseDto.setMentor(userToDto(course.getMentor()));
        return courseDto;
    }

    public Course toEntity(CourseDto courseDto) {
        return mapper.map(courseDto, Course.class);
    }

    public Course toEntity(CourseCreateDto courseCreateDto) {
        return mapper.map(courseCreateDto, Course.class);
    }

    public void toEntity(CourseDto courseDto, Course course) {
        mapper.map(courseDto, course);
    }

    public FieldDto toDto(Field field) {
        return mapper.map(field, FieldDto.class);
    }

    public Field toEntity(FieldDto fieldDto) {
        return mapper.map(fieldDto, Field.class);
    }

    public Field toEntity(FieldCreateDto fieldDto) {
        return mapper.map(fieldDto, Field.class);
    }

    public void toEntity(FieldDto fieldDto, Field field) {
        mapper.map(fieldDto, field);
    }

    public UserDto toDto(User user) {
        return mapper.map(user, UserDto.class);
    }

    public User toEntity(UserDto userDto) {
        return mapper.map(userDto, User.class);
    }

    public void toEntity(UserDto userDto, User user) {
        mapper.map(userDto, user);
    }

    public SessionDto toDto(Session session) {
        SessionDto sessionDto = mapper.map(session, SessionDto.class);
        List<ActivityDto> activityDtos = session.getActivities().stream()
                .map(this::toDto)
                .toList();
        sessionDto.setActivities(activityDtos);
        return sessionDto;
    }

    public Session toEntity(SessionDto sessionDto) {
        return mapper.map(sessionDto, Session.class);
    }

    public Session toEntity(SessionCreateDto sessionCreateDto) {
        return mapper.map(sessionCreateDto, Session.class);
    }

    public void toEntity(SessionUpdateDto sessionDto, Session session) {
        mapper.map(sessionDto, session);
    }

    public ActivityDto toDto(Activity activity) {
        ActivityDto dto = mapper.map(activity, ActivityDto.class);
        dto.setSessionId(activity.getSession().getId());
        return dto;
    }

    public void toDto(ActivityDto dto, Activity activity) {
        mapper.map(dto, activity);
    }

    public Activity toEntity(ActivityCreateDtoAlone dto) {
        return mapper.map(dto, Activity.class);
    }

    public Activity toEntity(ActivityDto activityDto) {
        return mapper.map(activityDto, Activity.class);
    }

    public Activity toEntity(ActivityCreateDto activityCreateDto) {
        return mapper.map(activityCreateDto, Activity.class);
    }

    public void toEntity(ActivityDto activityDto, Activity activity) {
        mapper.map(activityDto, activity);
    }

    public ClazzDto toDto(Clazz clazz) {
        return mapper.map(clazz, ClazzDto.class);
    }

    public Clazz toEntity(ClazzDto clazzDto) {
        return mapper.map(clazzDto, Clazz.class);
    }

    public Clazz toEntity(ClazzCreateDto clazzCreateDto) {
        return mapper.map(clazzCreateDto, Clazz.class);
    }

    public void toEntity(ClazzDto clazzDto, Clazz clazz) {
        mapper.map(clazzDto, clazz);
    }

    public LessonDto toDto(Lesson lesson) {
        LessonDto dto = mapper.map(lesson, LessonDto.class);
        dto.setClazzId(lesson.getClazz().getId());
        dto.setSessionId(lesson.getSession().getId());
        return dto;
    }

    public Lesson toEntity(LessonDto lessonDto) {
        return mapper.map(lessonDto, Lesson.class);
    }

    public Lesson toEntity(LessonCreateDto lessonCreateDto) {
        return mapper.map(lessonCreateDto, Lesson.class);
    }

    public void toEntity(LessonDto lessonDto, Lesson lesson) {
        mapper.map(lessonDto, lesson);
    }

    public Image toEntity(ImageDto dto) {
        return mapper.map(dto, Image.class);

    }
    public List<Image> toEntity (List<ImageDto> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
    public ImageDto toDto(Image entity) {
        return mapper.typeMap(Image.class, ImageDto.class)
                .addMappings(mapping -> mapping.map(image -> image.getCourse().getId(), ImageDto::setCourse))
                .map(entity);
    }

    public List<ImageDto> toDto(List<Image> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public UserProfileDto toUserProfileDto(User user) {
        return mapper.map(user, UserProfileDto.class);
    }

    public StudentDto toDto(Student student) {
        return mapper.map(student, StudentDto.class);
    }

    public void toEntity(StudentDto dto, Student student) {
        mapper.map(dto, student);
    }

    public MentorDto toDto(Mentor mentor) {
        return mapper.map(mentor, MentorDto.class);
    }

    public void toEntity(MentorDto dto, Mentor mentor) {
        mapper.map(dto, mentor);
    }
}
