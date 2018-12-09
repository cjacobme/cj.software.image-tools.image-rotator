package cj.software.imageTools.imageRotator;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javaxt.io.Image;

public class ImageRotator
{
	private Logger logger = LogManager.getFormatterLogger();

	public void rotateFile(Path pSource, Path pDestinationDir)
	{
		if (!Files.isDirectory(pDestinationDir))
		{
			throw new IllegalArgumentException(pDestinationDir + " is not a directory");
		}

		String lSourcePathStr = pSource.toString();

		Image lImage = new Image(lSourcePathStr);
		lImage.rotate();

		String lFileName = pSource.getFileName().toString();
		Path lDstnPath = Paths.get(pDestinationDir.toString(), lFileName);
		String lDstnPathStr = lDstnPath.toString();
		lImage.saveAs(lDstnPathStr);
		this.logger.info("saved %s rotated to %s", lSourcePathStr, lDstnPathStr);
	}

	public void rotateDirectory(Path pSourceDir, Path pDestinationDir) throws IOException
	{
		this.logger.info("rotate source directory %s", pSourceDir);
		if (!Files.isDirectory(pSourceDir))
		{
			throw new IllegalArgumentException(pSourceDir + " is not a directory");
		}
		if (!Files.isDirectory(pDestinationDir))
		{
			throw new IllegalArgumentException(pDestinationDir + " is not a directory");
		}
		DirectoryStream<Path> lDirectoryStream = Files.newDirectoryStream(pSourceDir);
		for (Path bSourceFile : lDirectoryStream)
		{
			this.rotateFile(bSourceFile, pDestinationDir);
		}
	}
}
