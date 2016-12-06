/**
 * Created by Hexray on 06.12.2016.
 */
var courseNumbers = {};
courseNumbers["FIRST"] = "Перший";
courseNumbers["SECOND"] = "Другий";
courseNumbers["THIRD"] = "Третій";
courseNumbers["FOURTH"] = "Четвертий";
courseNumbers["FIFTH"] = "П'ятий";
courseNumbers["SIXTH"] = "Шостий";

// courseNumbers["Перший"] = "FIRST";
// courseNumbers["Другий"] = "SECOND";
// courseNumbers["Третій"] = "THIRD";
// courseNumbers["Четвертий"] = "FOURTH";
// courseNumbers["П'ятий"] = "FIFTH";
// courseNumbers["Шостий"] = "SIXTH";

var semesterNumbers = {};
semesterNumbers["FIRST"] = "Перший";
semesterNumbers["SECOND"] = "Другий";

// semesterNumbers["Перший"] = "FIRST";
// semesterNumbers["Другий"] = "SECOND";

var groupDegree = {};
groupDegree["BACHELOR"] = "Бакалавр";
groupDegree["MASTER"] = "Магістр";

var groupType = {};
groupType["GENERAL_FORM"] = "Денна форма";
groupType["DISTANCE_FORM"] = "Заочна форма";

function getCourse(key) {
    return courseNumbers[key];
}

function getSemester(key) {
    return semesterNumbers[key];
}

function getDegree(key) {
    return groupDegree[key];
}

function getType(key) {
    return groupType[key];
}