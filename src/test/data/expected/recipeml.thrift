namespace * default

enum typeType
{
	typeType_area,
	typeType_length,
	typeType_mass,
	typeType_other,
	typeType_volume,
	typeType_weight,
}

enum optionalType
{
	optionalType_no,
	optionalType_yes,
}

enum unitType
{
	unitType_C,
	unitType_F,
	unitType_K,
}

enum systemType
{
	systemType_Imperial,
	systemType_SI,
	systemType_US,
	systemType_metric,
	systemType_other,
}

enum alternateType
{
	alternateType_no,
	alternateType_yes,
}

enum versionType
{
	versionType_0_5,
}

enum nameType
{
	nameType_DC_Contributor,
	nameType_DC_Coverage,
	nameType_DC_Creator,
	nameType_DC_Date,
	nameType_DC_Description,
	nameType_DC_Format,
	nameType_DC_Identifier,
	nameType_DC_Language,
	nameType_DC_Publisher,
	nameType_DC_Relation,
	nameType_DC_Rights,
	nameType_DC_Source,
	nameType_DC_Subject,
	nameType_DC_Title,
	nameType_DC_Type,
}

struct UnspecifiedType
{
	1 : required string baseObjectType,
	2 : required binary object,
}

struct tempunitType
{
	1 : optional string id,
	2 : optional string _class,
	3 : optional UnspecifiedType title,
	4 : optional string lang,
	5 : optional systemType system,
	6 : optional unitType unit,
}

struct productType
{
	1 : optional list<UnspecifiedType> inline_class,
	2 : optional string id,
	3 : optional string _class,
	4 : optional UnspecifiedType title,
	5 : optional string lang,
	6 : optional string uccIcn,
}

struct prodcodeType
{
	1 : optional string id,
	2 : required UnspecifiedType type,
	3 : required UnspecifiedType content,
	4 : optional UnspecifiedType domain,
}

struct noteType
{
	1 : optional UnspecifiedType measure_class,
	2 : optional UnspecifiedType inline_class,
	3 : optional string id,
	4 : optional string _class,
	5 : optional UnspecifiedType title,
	6 : optional string lang,
	7 : optional UnspecifiedType type,
}

struct mfrType
{
	1 : optional list<UnspecifiedType> inline_class,
	2 : optional string id,
	3 : optional string _class,
	4 : optional UnspecifiedType title,
	5 : optional string lang,
	6 : optional string uccMin,
}

struct ing_noteType
{
	1 : optional UnspecifiedType measure_class,
	2 : optional UnspecifiedType inline_class,
	3 : optional string id,
	4 : optional string _class,
	5 : optional UnspecifiedType title,
	6 : optional string lang,
}

struct unitType
{
	1 : optional systemType system,
	2 : optional string id,
	3 : optional string _class,
	4 : optional UnspecifiedType title,
	5 : optional string lang,
	6 : optional string unit,
	7 : optional typeType type,
}

struct descriptionType
{
	1 : optional UnspecifiedType measure_class,
	2 : optional UnspecifiedType inline_class,
	3 : optional string id,
	4 : optional string _class,
	5 : optional UnspecifiedType title,
	6 : optional string lang,
}

struct actionType
{
	1 : optional UnspecifiedType measure_class,
	2 : optional UnspecifiedType inline_class,
	3 : optional string id,
	4 : optional string _class,
	5 : optional UnspecifiedType title,
	6 : optional string lang,
	7 : optional UnspecifiedType code,
}

struct srcitemType
{
	1 : optional list<UnspecifiedType> inline_class,
	2 : optional string id,
	3 : optional string _class,
	4 : optional UnspecifiedType title,
	5 : optional string lang,
	6 : optional UnspecifiedType type,
}

struct spanType
{
	1 : optional list<UnspecifiedType> inline_class,
	2 : optional string id,
	3 : optional string _class,
	4 : optional UnspecifiedType title,
	5 : optional string lang,
}

struct sepType
{
	1 : optional string id,
	2 : optional string _class,
	3 : optional UnspecifiedType title,
	4 : optional string lang,
}

struct substepType
{
	1 : optional list<UnspecifiedType> step_class,
	2 : optional string id,
	3 : optional string _class,
	4 : optional UnspecifiedType title,
	5 : optional string lang,
	6 : optional UnspecifiedType duration,
	7 : optional UnspecifiedType type,
	8 : optional optionalType _optional,
}

struct versionType
{
	1 : optional string id,
	2 : optional string _class,
	3 : optional UnspecifiedType title,
	4 : optional string lang,
}

struct stepType
{
	1 : optional UnspecifiedType step_class,
	2 : optional substepType substep,
	3 : optional string id,
	4 : optional string _class,
	5 : optional UnspecifiedType title,
	6 : optional string lang,
	7 : optional UnspecifiedType duration,
	8 : optional UnspecifiedType type,
	9 : optional optionalType _optional,
}

struct conditionType
{
	1 : optional UnspecifiedType measure_class,
	2 : optional UnspecifiedType inline_class,
	3 : optional string id,
	4 : optional string _class,
	5 : optional UnspecifiedType title,
	6 : optional string lang,
	7 : optional UnspecifiedType code,
}

struct brandnameType
{
	1 : optional mfrType mfr,
	2 : optional productType product,
	3 : optional string id,
	4 : optional string _class,
	5 : optional UnspecifiedType title,
	6 : optional string lang,
}

struct n_nameType
{
	1 : optional string id,
	2 : optional string _class,
	3 : optional UnspecifiedType title,
	4 : optional string lang,
	5 : optional alternateType alternate,
}

struct fracType
{
	1 : required string n,
	2 : optional sepType sep,
	3 : required string d,
	4 : optional string id,
	5 : optional string _class,
	6 : optional UnspecifiedType title,
	7 : optional string lang,
}

struct timeunitType
{
	1 : optional systemType system,
	2 : optional string id,
	3 : optional string _class,
	4 : optional UnspecifiedType title,
	5 : optional string lang,
	6 : optional string unit,
}

struct titleType
{
	1 : optional brandnameType brandname,
	2 : optional UnspecifiedType inline_class,
	3 : optional string id,
	4 : optional string _class,
	5 : optional UnspecifiedType title,
	6 : optional string lang,
}

struct settingType
{
	1 : optional UnspecifiedType measure_class,
	2 : optional UnspecifiedType inline_class,
	3 : optional string id,
	4 : optional string _class,
	5 : optional UnspecifiedType title,
	6 : optional string lang,
	7 : optional UnspecifiedType value,
}

struct sourceType
{
	1 : optional list<srcitemType> srcitem,
	2 : optional string id,
	3 : optional string _class,
	4 : optional UnspecifiedType title,
	5 : optional string lang,
}

struct qtyType
{
	1 : optional list<fracType> frac,
	2 : optional string id,
	3 : optional string _class,
	4 : optional UnspecifiedType title,
}

struct itemType
{
	1 : optional brandnameType brandname,
	2 : optional UnspecifiedType inline_class,
	3 : optional string id,
	4 : optional string _class,
	5 : optional UnspecifiedType title,
	6 : optional string lang,
}

struct subtitleType
{
	1 : optional brandnameType brandname,
	2 : optional UnspecifiedType inline_class,
	3 : optional string id,
	4 : optional string _class,
	5 : optional UnspecifiedType title,
	6 : optional string lang,
}

struct metaType
{
	1 : required nameType name,
	2 : required UnspecifiedType content,
	3 : optional UnspecifiedType scheme,
	4 : optional UnspecifiedType lang,
}

struct catType
{
	1 : optional string id,
	2 : optional string _class,
	3 : optional UnspecifiedType title,
	4 : optional string lang,
	5 : optional UnspecifiedType type,
}

struct percentType
{
	1 : optional list<fracType> frac,
	2 : optional UnspecifiedType standard,
}

struct q2Type
{
	1 : optional list<fracType> frac,
	2 : optional string id,
	3 : optional string _class,
	4 : optional UnspecifiedType title,
}

struct nutrientType
{
	1 : required list<n_nameType> n_name,
	2 : optional qtyType qty,
	3 : optional unitType unit,
	4 : optional percentType percent,
	5 : optional string id,
	6 : optional string _class,
	7 : optional UnspecifiedType title,
	8 : optional string lang,
}

struct q1Type
{
	1 : optional list<fracType> frac,
	2 : optional string id,
	3 : optional string _class,
	4 : optional UnspecifiedType title,
}

struct categoriesType
{
	1 : optional list<catType> cat,
	2 : optional string id,
	3 : optional string _class,
	4 : optional UnspecifiedType title,
	5 : optional string lang,
	6 : optional list_string_ content,
}

struct nutrient_groupType
{
	1 : optional titleType title,
	2 : required list<nutrientType> nutrient,
	3 : optional string id,
	4 : optional string _class,
	5 : optional string lang,
	6 : optional UnspecifiedType standard,
}

struct rangeType
{
	1 : required q1Type q1,
	2 : optional sepType sep,
	3 : required q2Type q2,
	4 : optional string id,
	5 : optional string _class,
	6 : optional UnspecifiedType title,
}

struct exchangeType
{
	1 : optional qtyType qty,
	2 : optional rangeType range,
	3 : optional string id,
	4 : optional string _class,
	5 : optional UnspecifiedType title,
	6 : optional string lang,
}

struct nutritionType
{
	1 : optional nutrient_groupType nutrient_group,
	2 : optional nutrientType nutrient,
	3 : optional string id,
	4 : optional string _class,
	5 : optional UnspecifiedType title,
	6 : optional string lang,
	7 : optional UnspecifiedType standard,
}

struct timeType
{
	1 : optional qtyType qty,
	2 : optional rangeType range,
	3 : required timeunitType timeunit,
	4 : optional string id,
	5 : optional string _class,
	6 : optional UnspecifiedType title,
}

struct sizeType
{
	1 : optional qtyType qty,
	2 : optional rangeType range,
	3 : optional unitType unit,
	4 : optional string id,
	5 : optional string _class,
	6 : optional UnspecifiedType title,
	7 : optional string lang,
	8 : optional string code,
}

struct yieldType
{
	1 : optional qtyType qty,
	2 : optional rangeType range,
	3 : optional unitType unit,
	4 : optional string id,
	5 : optional string _class,
	6 : optional UnspecifiedType title,
	7 : optional string lang,
}

struct amtType
{
	1 : optional qtyType qty,
	2 : optional rangeType range,
	3 : optional sizeType size,
	4 : optional unitType unit,
	5 : optional string id,
	6 : optional string _class,
	7 : optional UnspecifiedType title,
	8 : optional string lang,
	9 : optional systemType system,
	10 : optional UnspecifiedType variation,
}

struct tempType
{
	1 : optional qtyType qty,
	2 : optional rangeType range,
	3 : required tempunitType tempunit,
	4 : optional string id,
	5 : optional string _class,
	6 : optional UnspecifiedType title,
}

struct time_cont
{
	1 : required timeType time,
	2 : optional sepType sep,
}

struct toolrefType
{
	1 : optional brandnameType brandname,
	2 : optional qtyType qty,
	3 : optional rangeType range,
	4 : optional sizeType size,
	5 : optional unitType unit,
	6 : optional UnspecifiedType inline_class,
	7 : optional string id,
	8 : optional string _class,
	9 : optional UnspecifiedType title,
	10 : optional string lang,
	11 : required string toolid,
}

struct ingrefType
{
	1 : optional brandnameType brandname,
	2 : optional qtyType qty,
	3 : optional rangeType range,
	4 : optional sizeType size,
	5 : optional unitType unit,
	6 : optional UnspecifiedType inline_class,
	7 : optional string id,
	8 : optional string _class,
	9 : optional UnspecifiedType title,
	10 : optional string lang,
	11 : required string ingid,
}

struct modifierType
{
	1 : optional sizeType size,
	2 : optional UnspecifiedType measure_class,
	3 : optional UnspecifiedType inline_class,
	4 : optional string id,
	5 : optional string _class,
	6 : optional UnspecifiedType title,
	7 : optional string lang,
}

struct toolType
{
	1 : optional brandnameType brandname,
	2 : optional qtyType qty,
	3 : optional rangeType range,
	4 : optional sizeType size,
	5 : optional unitType unit,
	6 : optional UnspecifiedType inline_class,
	7 : optional string id,
	8 : optional string _class,
	9 : optional UnspecifiedType title,
	10 : optional string lang,
	11 : optional optionalType _optional,
}

struct diet_exchangesType
{
	1 : optional list<exchangeType> exchange,
	2 : optional string id,
	3 : optional string _class,
	4 : optional UnspecifiedType title,
	5 : optional string lang,
	6 : optional UnspecifiedType authority,
}

struct preptimeType
{
	1 : required timeType time,
	2 : optional sepType sep,
	3 : optional string id,
	4 : optional string _class,
	5 : optional UnspecifiedType title,
	6 : optional string lang,
	7 : required UnspecifiedType type,
}

struct prepType
{
	1 : optional sizeType size,
	2 : optional UnspecifiedType measure_class,
	3 : optional UnspecifiedType inline_class,
	4 : optional string id,
	5 : optional string _class,
	6 : optional UnspecifiedType title,
	7 : optional string lang,
}

struct headType
{
	1 : required titleType title,
	2 : optional subtitleType subtitle,
	3 : optional versionType version,
	4 : optional sourceType source,
	5 : optional categoriesType categories,
	6 : optional list<preptimeType> preptime,
	7 : optional yieldType _yield,
}

struct equip_divType
{
	1 : optional titleType title,
	2 : optional descriptionType description,
	3 : optional list<noteType> note,
	4 : required toolType tool,
	5 : optional string id,
	6 : optional string _class,
	7 : optional string lang,
	8 : optional systemType system,
	9 : optional typeType type,
}

struct ing_cont
{
	1 : optional modifierType modifier,
	2 : required itemType item,
	3 : optional prepType prep,
	4 : optional list<ing_noteType> ing_note,
	5 : optional list<prodcodeType> prodcode,
}

struct alt_ingType
{
	1 : optional modifierType modifier,
	2 : required itemType item,
	3 : optional prepType prep,
	4 : optional list<ing_noteType> ing_note,
	5 : optional list<prodcodeType> prodcode,
	6 : optional string id,
	7 : optional string _class,
	8 : optional UnspecifiedType title,
	9 : optional string lang,
}

struct ingType
{
	1 : optional modifierType modifier,
	2 : required itemType item,
	3 : optional prepType prep,
	4 : optional list<ing_noteType> ing_note,
	5 : optional list<prodcodeType> prodcode,
	6 : optional list<alt_ingType> alt_ing,
	7 : optional string id,
	8 : optional string _class,
	9 : optional UnspecifiedType title,
	10 : optional string lang,
	11 : optional optionalType _optional,
}

struct ing_divType
{
	1 : optional titleType title,
	2 : optional descriptionType description,
	3 : optional list<noteType> note,
	4 : required ingType ing,
	5 : optional string id,
	6 : optional string _class,
	7 : optional string lang,
	8 : optional systemType system,
	9 : optional typeType type,
}

struct equipmentType
{
	1 : optional list<equip_divType> equip_div,
	2 : optional list<noteType> note,
	3 : optional toolType tool,
	4 : optional systemType system,
	5 : optional string id,
	6 : optional string _class,
	7 : optional UnspecifiedType title,
	8 : optional string lang,
}

struct ingredientsType
{
	1 : optional list<ing_divType> ing_div,
	2 : optional list<noteType> note,
	3 : optional ingType ing,
	4 : optional systemType system,
	5 : optional string id,
	6 : optional string _class,
	7 : optional UnspecifiedType title,
	8 : optional string lang,
}

struct dir_divType
{
	1 : optional titleType title,
	2 : optional descriptionType description,
	3 : optional noteType note,
	4 : optional ingType ing,
	5 : required stepType step,
	6 : optional string id,
	7 : optional string _class,
	8 : optional string lang,
	9 : optional systemType system,
	10 : optional typeType type,
	11 : optional UnspecifiedType duration,
}

struct directionsType
{
	1 : optional list<dir_divType> dir_div,
	2 : optional noteType note,
	3 : optional ingType ing,
	4 : optional stepType step,
	5 : optional systemType system,
	6 : optional string id,
	7 : optional string _class,
	8 : optional UnspecifiedType title,
	9 : optional string lang,
}

struct recipeType
{
	1 : required headType head,
	2 : optional list<descriptionType> description,
	3 : optional equipmentType equipment,
	4 : required ingredientsType ingredients,
	5 : required directionsType directions,
	6 : optional nutritionType nutrition,
	7 : optional diet_exchangesType diet_exchanges,
	8 : optional string id,
	9 : optional string _class,
	10 : optional UnspecifiedType title,
	11 : optional string lang,
	12 : optional systemType system,
}

struct menuType
{
	1 : required headType head,
	2 : optional list<descriptionType> description,
	3 : optional list<recipeType> recipe,
	4 : optional systemType system,
	5 : optional string id,
	6 : optional string _class,
	7 : optional UnspecifiedType title,
	8 : optional string lang,
}

struct recipemlType
{
	1 : optional list<metaType> meta,
	2 : optional menuType menu,
	3 : optional recipeType recipe,
	4 : optional string id,
	5 : optional string _class,
	6 : optional UnspecifiedType title,
	7 : optional string lang,
	8 : optional versionType version,
	9 : optional UnspecifiedType generator,
}

