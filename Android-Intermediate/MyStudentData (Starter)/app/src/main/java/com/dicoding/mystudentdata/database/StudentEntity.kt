package com.dicoding.mystudentdata.database

import androidx.room.*

@Entity
data class Student(
    @PrimaryKey
    val studentId: Int,
    val name: String,
    val univId: Int,
)

@Entity
data class University(
    @PrimaryKey
    val universityId: Int,
    val name: String,
)

@Entity
data class Course(
    @PrimaryKey
    val courseId: Int,
    val name: String,
)

// Relasi Many to One
data class StudentAndUniversity(
    @Embedded
    val student: Student,
    @Relation(
        parentColumn = "univId", // Primary key dari Student
        entityColumn = "universityId" // Foreign key dari University
    )
    val university: University? = null
)

// Relasi One to Many
data class UniversityAndStudent(
    @Embedded
    val university: University,
    @Relation(
        parentColumn = "universityId",
        entityColumn = "univId"
    )
    val student: List<Student>
)

// Relasi many to many
@Entity(primaryKeys = ["sId", "cId"])
data class CourseStudentCrossRef(
    val sId: Int,
    @ColumnInfo(index = true)
    val cId: Int,
)

data class StudentWithCourse(
    @Embedded
    val studentAndUniversity: StudentAndUniversity,
    @Relation(
        parentColumn = "studentId",
        entity = Course::class,
        entityColumn = "courseId",
        associateBy = Junction(
            value = CourseStudentCrossRef::class,
            parentColumn = "sId",
            entityColumn = "cId"
        )
    )
    val course: List<Course>
)