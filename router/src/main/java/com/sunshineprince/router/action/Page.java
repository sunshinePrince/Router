/*
 * Copyright (C) 2016
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sunshineprince.router.action;

import java.util.ArrayList;

/**
 * author : sunny
 * email : zicai346@gmail.com
 * github : https://github.com/sunshinePrince
 * blog : http://mrjoker.wang
 */
public class Page {


	public static final String NAME = "name";


	public static final String CLASS = "class";


	public static final String FORWARD = "forward";



	public String name;


	public String className;

	public String forward;

	public ArrayList<String> interceptors = new ArrayList<>();






}
