CREATE DATABASE school_db;
use school_db;
CREATE TABLE students(
student_id int primary key auto_increment,
student_name varchar(50) not null,
student_email varchar(100) unique,
student_age int check(student_age>5),
student_city varchar(100) default 'delhi',
student_admission_date date
);
 create table marks(
 mark_id int primary key,
 student_id int ,
 subject_ varchar(30),
 score_ int,
foreign key (student_id) references students(student_id)
 );
 alter table students
 add phone_no int;
 alter table students
 modify phone_no varchar(15);
 alter table students
 rename column student_city to student_hometown;
 alter table students
 add index idx_students_name(student_name);


INSERT INTO students
    (student_name, student_email, student_age, student_hometown, student_admission_date)
VALUES
    ('Rahul Sharma', 'rahul@example.com', 20, 'Delhi', '2024-07-15'),
    ('Priya Singh', 'priya@example.com', 21, 'Mumbai', '2024-07-20'),
    ('Amit Kumar', 'amit@example.com', 19, 'Lucknow', '2024-08-01');
    INSERT INTO marks
    (mark_id, student_id, subject_, score_)
VALUES
    (1, 1, 'Java', 85),
    (2, 1, 'SQL', 90),
    (3, 2, 'Java', 78),
    (4, 2, 'SQL', 88),
    (5, 3, 'Java', 92),
    (6, 3, 'SQL', 95);
select  * from  students;
select * from marks;
 CREATE TABLE students_backup LIKE students;
UPDATE marks
SET score_ = score_ + 5
WHERE mark_id IN (1, 3, 5)
and subject_ = 'Java';
update students
set student_hometown='noida'
where student_id=2;

update students
set student_age=student_age+1
where  student_hometown='delhi';

select  * from  students;
select * from marks;
