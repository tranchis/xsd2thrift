namespace * default

enum typeType
{
	typeType_html,
	typeType_text,
	typeType_xhtml,
}

struct UnspecifiedType
{
	1 : required string baseObjectType,
	2 : required binary object,
}

struct categoryType
{
	1 : optional UnspecifiedType base,
	2 : optional string label,
	3 : optional string lang,
	4 : optional UnspecifiedType scheme,
	5 : required string term,
}

struct contentType
{
	1 : optional UnspecifiedType base,
	2 : optional string lang,
	3 : optional UnspecifiedType src,
	4 : optional string type,
}

struct dateTimeType
{
	1 : optional UnspecifiedType base,
	2 : optional string lang,
}

struct generatorType
{
	1 : optional UnspecifiedType base,
	2 : optional string lang,
	3 : optional UnspecifiedType uri,
	4 : optional string version,
}

struct iconType
{
	1 : optional UnspecifiedType base,
	2 : optional string lang,
}

struct idType
{
	1 : optional UnspecifiedType base,
	2 : optional string lang,
}

struct linkType
{
	1 : optional UnspecifiedType base,
	2 : required UnspecifiedType href,
	3 : optional string hreflang,
	4 : optional string lang,
	5 : optional i64 length,
	6 : optional string rel,
	7 : optional string title,
	8 : optional string type,
}

struct logoType
{
	1 : optional UnspecifiedType base,
	2 : optional string lang,
}

struct textType
{
	1 : optional UnspecifiedType base,
	2 : optional string lang,
	3 : optional typeType type,
}

struct uriType
{
	1 : optional UnspecifiedType base,
	2 : optional string lang,
}

struct personType
{
	1 : optional UnspecifiedType base,
	2 : optional string email,
	3 : optional string lang,
	4 : optional string name,
	5 : optional uriType uri,
}

struct sourceType
{
	1 : optional list<personType> author,
	2 : optional UnspecifiedType base,
	3 : optional list<categoryType> category,
	4 : optional list<personType> contributor,
	5 : optional generatorType generator,
	6 : optional iconType icon,
	7 : optional idType id,
	8 : optional string lang,
	9 : optional list<linkType> link,
	10 : optional logoType logo,
	11 : optional textType rights,
	12 : optional textType subtitle,
	13 : optional textType title,
	14 : optional dateTimeType updated,
}

struct entryType
{
	1 : optional list<personType> author,
	2 : optional UnspecifiedType base,
	3 : optional list<categoryType> category,
	4 : optional contentType content,
	5 : optional list<personType> contributor,
	6 : optional idType id,
	7 : optional string lang,
	8 : optional list<linkType> link,
	9 : optional dateTimeType published,
	10 : optional textType rights,
	11 : optional textType source,
	12 : optional textType summary,
	13 : optional textType title,
	14 : optional dateTimeType updated,
}

struct feedType
{
	1 : optional list<personType> author,
	2 : optional UnspecifiedType base,
	3 : optional list<categoryType> category,
	4 : optional list<personType> contributor,
	5 : optional list<entryType> entry,
	6 : optional generatorType generator,
	7 : optional iconType icon,
	8 : optional idType id,
	9 : optional string lang,
	10 : optional list<linkType> link,
	11 : optional logoType logo,
	12 : optional textType rights,
	13 : optional textType subtitle,
	14 : optional textType title,
	15 : optional dateTimeType updated,
}

