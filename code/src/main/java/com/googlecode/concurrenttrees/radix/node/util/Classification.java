package com.googlecode.concurrenttrees.radix.node.util;

public enum Classification {
	EXACT_MATCH,
    INCOMPLETE_MATCH_TO_END_OF_EDGE,
    INCOMPLETE_MATCH_TO_MIDDLE_OF_EDGE,
    KEY_ENDS_MID_EDGE,
    INVALID // INVALID is never used, except in unit testing
}
