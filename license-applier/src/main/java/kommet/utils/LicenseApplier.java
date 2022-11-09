/**
 * Copyright 2022, Kommet
 * https://kommet.io
 * 
 */

package kommet.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LicenseApplier
{	
	public static void main(String[] args) throws IOException
	{
		// process all files starting with the current directory
		walk(".");
	}
	
	private static void walk (String path) throws IOException
	{
		File root = new File( path );
        File[] list = root.listFiles();

        if (list == null)
        {
        	return;
        }

        for (File f : list)
        {
            if (f.isDirectory())
            {
                walk(f.getAbsolutePath());
                System.out.println("Dir:" + f.getAbsoluteFile());
            }
            else
            {
                System.out.println("File:" + f.getAbsoluteFile());
                
                if (f.getName().endsWith(".js") || f.getName().endsWith(".java"))
                {
                	processFile(f);
                }
            }
        }
	}
	
	/**
	 * Prepend copyright formula to each file.
	 * @param f
	 * @throws IOException
	 */
	private static void processFile (File f) throws IOException
	{
		List<String> lines = new ArrayList<String>();
		lines.add("/**");
		lines.add(" * Copyright 2022, Kommet");
		lines.add(" * Licensed under the GNU Affera General Public License (the \"License\");");
		lines.add(" * you may not use this file except in compliance with the License.");
		lines.add(" * You may obtain a copy of the License at https://www.gnu.org/licenses/agpl-3.0.en.html"); 
		lines.add(" */");
		lines.add("");
		
		try (BufferedReader br = new BufferedReader(new FileReader(f)))
		{
			int lineNo = 0;
		    String line;
		    while ((line = br.readLine()) != null)
		    {
		    	if (lineNo == 0)
		    	{
		    		if (line.equals("/**"))
		    		{
		    			return;
		    		}
		    	}
		    	
		    	lines.add(line);
		    	lineNo++;
		    }
		}
		
		FileWriter fw = new FileWriter(f);
		int lineNo = 0;
		for (String line : lines)
		{
			fw.append(line);
			
			if (lineNo < lines.size() - 1)
			{
				fw.append("\n");
			}
			
			lineNo++;
		}
		
		fw.close();
	}
}
