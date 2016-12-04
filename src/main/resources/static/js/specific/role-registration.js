/**
 * Created by Алена on 01.12.2016.
 */
$(function () {
    fillSelectYear("YearId", API + "/years/getAll");
    fillSelectFrom("SpecialityId", API + "/specialities/getAll", "fullName");
});
