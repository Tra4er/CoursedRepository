/**
 * Created by Hexray on 06.12.2016.
 */
var localGroupUkr = {};
//EngToUkr
localGroupUkr["FIRST"] = "Перший";
localGroupUkr["SECOND"] = "Другий";
localGroupUkr["THIRD"] = "Третій";
localGroupUkr["FOURTH"] = "Четвертий";
localGroupUkr["FIFTH"] = "П'ятий";
localGroupUkr["SIXTH"] = "Шостий";
localGroupUkr["BACHELOR"] = "Бакалаврат";
localGroupUkr["MASTER"] = "Магістратура";
localGroupUkr["GENERAL_FORM"] = "Денна форма";
localGroupUkr["DISTANCE_FORM"] = "Заочна форма";
//UkrToEng

function localGroup(key) {
    return localGroupUkr[key];
}

//Partial loc for {each}
var courseNumbers = {};
courseNumbers["FIRST"] = "Перший";
courseNumbers["SECOND"] = "Другий";
courseNumbers["THIRD"] = "Третій";
courseNumbers["FOURTH"] = "Четвертий";
courseNumbers["FIFTH"] = "П'ятий";
courseNumbers["SIXTH"] = "Шостий";

var groupDegree = {};
groupDegree["BACHELOR"] = "Бакалаврат";
groupDegree["MASTER"] = "Магістратура";

var groupType = {};
groupType["GENERAL_FORM"] = "Денна форма";
groupType["DISTANCE_FORM"] = "Заочна форма";