package com.test.git;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;

public class GitTest {

	/**
	 * @param args
	 * @throws IOException
	 * @throws GitAPIException
	 */
	public static void main(String[] args) throws IOException, GitAPIException {
		final Git git = Git.open(new File(
				"D:/Sources/repositories/support-tool"));

		printRefs(git.tagList().call());
		printRefs(git.branchList().call());

		git.checkout().setName(git.tagList().call().get(0).getName()).call();
	}

	private static void printRefs(final List<Ref> refs) {
		for (Ref ref : refs) {
			System.out.println(ref.getName() + ":"
					+ ref.getObjectId().getName());
		}
	}
}
