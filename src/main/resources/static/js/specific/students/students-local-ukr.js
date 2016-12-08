/**
 * Created by Olena on 08.12.2016.
 */

var semesterNumbers = {};
semesterNumbers["FIRST"] = "Перший";
semesterNumbers["SECOND"] = "Другий";

// semesterNumbers["Перший"] = "FIRST";
// semesterNumbers["Другий"] = "SECOND";

var groupDegree = {};
groupDegree["BACHELOR"] = "Бакалавр";
groupDegree["MASTER"] = "Магістр";

var studentEducationStatus = {};
studentEducationStatus["STUDYING"] = "Навчається";
studentEducationStatus["FINISHED"] = "Закінчено";
studentEducationStatus["EXPELLED"] = "Виключено";

function getSemester(key) {
    return semesterNumbers[key];
}

function getStudentEducationStatus(key) {
    return studentEducationStatus[key];
}
