namespace * default

struct UnspecifiedType
{
	1 : required string baseObjectType,
	2 : required binary object,
}

struct TestRangeDecimal
{
	1 : optional double value,
}

struct TestRangeInt
{
	1 : optional i64 value,
}

