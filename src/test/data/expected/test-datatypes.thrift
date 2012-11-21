namespace * default

struct UnspecifiedType
{
	1 : required string baseObjectType,
	2 : required binary object,
}

struct Datatypes
{
	1 : required i64 a_positiveInteger,
	2 : required i32 an_int,
	3 : required i64 an_integer,
	4 : required i64 a_negativeInteger,
	5 : required i64 a_nonNegativeInteger,
	6 : required i64 a_nonPositiveInteger,
	7 : required int64 an_unsignedLong,
	8 : required int32 an_unsignedInt,
	9 : required int16 an_unsignedShort,
	10 : required byte an_unsignedByte,
	11 : required double a_float,
	12 : required double a_double,
	13 : required i16 a_short,
	14 : required i64 a_long,
	15 : required double a_decimal,
	16 : required string an_ID,
	17 : required string an_IDREF,
	18 : required string a_NMTOKEN,
	19 : required list_string_ a_NMTOKENS,
	20 : required UnspecifiedType an_anySimpleType,
	21 : required UnspecifiedType an_anyType,
	22 : required UnspecifiedType an_anyURI,
	23 : required bool a_boolean,
	24 : required binary a_base64Binary,
	25 : required binary a_hexBinary,
	26 : required byte a_byte,
	27 : required i32 a_date,
	28 : required i64 a_dateTime,
}

