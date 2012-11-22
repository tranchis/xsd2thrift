namespace * schemas.com.domain.address

include "schemas_com_domain_common.thrift"

struct addressLinesType
{
	1 : required list<string> line,
}

struct addressType
{
	1 : required addressLinesType addressLines,
	2 : required string city,
	3 : required string postCode,
	4 : required schemas_com_domain_common.country _country,
}

