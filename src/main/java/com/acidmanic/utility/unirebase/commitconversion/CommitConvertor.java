package com.acidmanic.utility.unirebase.commitconversion;

import com.acidmanic.utility.unirebase.models.CommitData;

public interface CommitConvertor<T>{



    CommitData convert(T value);
}
