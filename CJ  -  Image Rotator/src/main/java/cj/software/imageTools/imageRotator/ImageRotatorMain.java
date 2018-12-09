package cj.software.imageTools.imageRotator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImageRotatorMain
{
	private Logger logger = LogManager.getFormatterLogger();

	public static void main(String[] pArgs) throws IOException
	{
		Options lOptions = createOptions();
		CommandLineParser lCLP = new DefaultParser();
		try
		{
			CommandLine lCommandLine = lCLP.parse(lOptions, pArgs);
			ImageRotatorMain lInstance = new ImageRotatorMain();
			lInstance.rotate(lCommandLine);
		}
		catch (ParseException e)
		{
			printHelp(lOptions);
		}
	}

	private static Options createOptions()
	{
		Option lSource = Option
				.builder("source")
				.argName("sourceFileOrDir")
				.desc("source file or directory")
				.hasArg()
				.required()
				.build();
		Option lDstn = Option
				.builder("destination")
				.argName("dstn")
				.desc("destination directory")
				.hasArg()
				.required()
				.build();
		Options lResult = new Options();
		lResult.addOption(lSource);
		lResult.addOption(lDstn);
		return lResult;
	}

	private static void printHelp(Options pOptions)
	{
		HelpFormatter lFormatter = new HelpFormatter();
		lFormatter.printHelp(ImageRotatorMain.class.getName(), pOptions);
	}

	private void rotate(CommandLine pCommandLine) throws IOException
	{
		String lSource = pCommandLine.getOptionValue("source");
		String lDestination = pCommandLine.getOptionValue("destination");
		this.logger.info("rotate source=%s, destination=%s", lSource, lDestination);

		Path lSourcePath = Paths.get(lSource);
		Path lDstnPath = Paths.get(lDestination);
		ImageRotator lImageRotator = new ImageRotator();
		if (Files.isDirectory(lSourcePath))
		{
			lImageRotator.rotateDirectory(lSourcePath, lDstnPath);
		}
		else
		{
			lImageRotator.rotateFile(lSourcePath, lDstnPath);
		}
	}
}
