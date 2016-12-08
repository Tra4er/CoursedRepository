/**
 * Created by Olena on 08.12.2016.
 */
var localStudentUkr = {};
//EngToUkr
localStudentUkr["FIRST"] = "Перший";
localStudentUkr["SECOND"] = "Другий";
localStudentUkr["BACHELOR"] = "Бакалавр";
localStudentUkr["MASTER"] = "Магістр";
localStudentUkr["STUDYING"] = "Навчається";
localStudentUkr["FINISHED"] = "Закінчено";
localStudentUkr["EXPELLED"] = "Виключено";
//UkrToEng

function localStudent(key) {
    return localStudentUkr[key];
}

//Partial loc for {each}
var semesterNumbers = {};
semesterNumbers["FIRST"] = "Перший";
semesterNumbers["SECOND"] = "Другий";

var groupDegree = {};
groupDegree["BACHELOR"] = "Бакалавр";
groupDegree["MASTER"] = "Магістр";

var studentEducationStatus = {};
studentEducationStatus["STUDYING"] = "Навчається";
studentEducationStatus["FINISHED"] = "Закінчено";
studentEducationStatus["EXPELLED"] = "Виключено";
