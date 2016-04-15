namespace * default

struct UnspecifiedType
{
	1 : required string baseObjectType,
	2 : required binary object,
}

struct Datatypes
{
	1 : required string a_NMTOKEN,
	2 : required list_string_ a_NMTOKENS,
	3 : required binary a_base64Binary,
	4 : required bool a_boolean,
	5 : required byte a_byte,
	6 : required i32 a_date,
	7 : required i64 a_dateTime,
	8 : required double a_decimal,
	9 : required double a_double,
	10 : required double a_float,
	11 : required binary a_hexBinary,
	12 : required i64 a_long,
	13 : required i64 a_negativeInteger,
	14 : required i64 a_nonNegativeInteger,
	15 : required i64 a_nonPositiveInteger,
	16 : required i64 a_positiveInteger,
	17 : required i16 a_short,
	18 : required string an_ID,
	19 : required string an_IDREF,
	20 : required UnspecifiedType an_anySimpleType,
	21 : required UnspecifiedType an_anyType,
	22 : required UnspecifiedType an_anyURI,
	23 : required i32 an_int,
	24 : required i64 an_integer,
	25 : required byte an_unsignedByte,
	26 : required int32 an_unsignedInt,
	27 : required int64 an_unsignedLong,
	28 : required int16 an_unsignedShort,
}

